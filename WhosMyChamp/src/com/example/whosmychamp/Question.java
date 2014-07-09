package com.example.whosmychamp;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.view.WindowManager.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

public class Question extends Activity {

	// The number of questions that will be asked
	public final int numQuestions = 10;
	public int currentQuestionNumber = 0;
	
	public ArrayList<String> questions = new ArrayList<String>(); // Question list completed
	public ArrayList<Champion> champions = new ArrayList<Champion>(); // In progress
	public ArrayList<Champion> temporary = new ArrayList<Champion>(); // In progress
	public ArrayList<Champion> history = new ArrayList<Champion>(); // In progress
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.question);
	    
	    Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
	    TextView myTextView = (TextView) findViewById(R.id.questionView);
	    myTextView.setTypeface(typeFace);
	    RadioButton rb = (RadioButton) findViewById(R.id.option1);
	    rb.setTypeface(typeFace);
	    rb = (RadioButton) findViewById(R.id.option2);
	    rb.setTypeface(typeFace);
	    rb = (RadioButton) findViewById(R.id.option3);
	    rb.setTypeface(typeFace);
	    rb = (RadioButton) findViewById(R.id.option4);
	    rb.setTypeface(typeFace);
	    rb = (RadioButton) findViewById(R.id.option5);
	    rb.setTypeface(typeFace);
	    rb = (RadioButton) findViewById(R.id.option6);
	    rb.setTypeface(typeFace);
	    
    	// Load xml question data file
    	try{
    		XmlPullParser customList = null;
    	    if(MainActivity.isEnglish == true){
    	    	// Load English Questions
    	    	customList = getResources().getXml(R.xml.questions_eng);
    	    }else{
    	    	// Load Korean Questions
    	    	//customList = getResources().getXml(R.xml.questions_kor);
    	    }
    		while(customList.getEventType() != XmlPullParser.END_DOCUMENT){
    			if(customList.getEventType() == XmlPullParser.START_TAG){
    				if(customList.getName().equals("option")){
    					questions.add(customList.getAttributeValue(0));
    				}
    			}
    			customList.next();
    		}
    		
    		// Load champion xml data file
       		int j = 0;
       		String[] temp;
       		ArrayList<String> read;
       		Champion aChampion = null;
    		XmlPullParser champList = getResources().getXml(R.xml.champion_data);
    		while(champList.getEventType() != XmlPullParser.END_DOCUMENT){
    			if(champList.getEventType() == XmlPullParser.START_TAG){
    				if(champList.getName().equals("option")){
    					switch (j){
    						case 0:
    							aChampion = new Champion();
    							aChampion.setName(champList.getAttributeValue(0));
    							j++;
    							break;
    						case 1:// need to save it in array
    							temp = ((String)champList.getAttributeValue(0)).split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setLane(read);
    							j++;
    							break;
    						case 2:
    							aChampion.setPopularity(champList.getAttributeValue(0).equals("Yes") ? 1 : 0);
    							j++;
    							break;
    						case 3:
    							aChampion.setDamage_style(champList.getAttributeValue(0));
    							j++;
    							break;
    						case 4:
    							aChampion.setAppearance(champList.getAttributeValue(0));
    							j++;
    							break;
    						case 5:
    							aChampion.setPrice(Integer.parseInt(champList.getAttributeValue(0)));
    							j++;
    							break;
    						case 6:
    							aChampion.setType(champList.getAttributeValue(0));
    							j++;
    							break;
    						case 7:
    							temp = champList.getAttributeValue(0).split("$");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setActiveSkill(read);
    							j++;
    							break;
    						case 8:
    							temp = champList.getAttributeValue(0).split("$");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setPassiveSkill(read);
    							j++;
    							break;
    						case 9:
    							aChampion.setDifficulty(Integer.parseInt(champList.getAttributeValue(0)));
    							j++;
    							break;
    						case 10:
    							aChampion.setSkillType(champList.getAttributeValue(0));
    							champions.add(aChampion);
    							j = 0;
    							break;
    						default :
    							break;
    					}
    				}
    			}
    			champList.next();
    		}
    	}catch(XmlPullParserException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	//champion list layout
    	LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		for (int i = 0; i < champions.size(); i++){
    		Button imgbtn = new Button(this);
    		//int resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getName().toLowerCase(), "drawable", "com.example.whosmychamp");
    		//imgbtn.setBackgroundResource(resID);
    		imgbtn.setText("asaa");
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				View popupView = getLayoutInflater().inflate(R.layout.result, null);
    				PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    				 mPopupWindow.setAnimationStyle(-1);
    				 mPopupWindow.showAsDropDown(v, 10, 10);    		 
    			}
    		});
    		scrollBar.addView(imgbtn);
		}
    	
    	nextQuestion();
    	
    	Button nextButton = (Button)findViewById(R.id.nextButton);
    	nextButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(currentQuestionNumber != numQuestions){
					// on each next click you need to filter the hero list and save the filtered hero objects to somewhere else
					
					//startActivity(new Intent(Question.this, Question.class));
					filterChampion();
					killPopups();
					currentQuestionNumber++;
					nextQuestion();
				}else{
					//startActivity(new Intent(Question.this, Result_List.class));
					//deactivate next button
				}
			}
		});
	}
	
	private void killPopups(){
		while(findViewByID(R.id.))
	}
	
	private boolean isInclusion(int i, String option){
		//question1
		for(int k = 0; k < champions.get(i).getLane().size(); k++){
			if(champions.get(i).getLane().get(k).equals(option)){
				return true;
			}
		}
		return false;
	}
	
	private int question1(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(isInclusion(i, "Top")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(isInclusion(i, "Mid")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(isInclusion(i, "Jungle")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(isInclusion(i, "Bot(Marksman)")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option5)).isChecked()){
			if(isInclusion(i, "Bot(Sup)")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			//pass
		}
		return i;
	}
	
	private int question2(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPopularity() == 1){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPopularity() == 0){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			//pass
		}
		return i;
	}
	
	private int question3(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getDamage_style().equals("Long")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getDamage_style().equals("Close")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			//pass
		}
		return i;
	}
	
	private int question4(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getAppearance().equals("Human")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getAppearance().equals("Animal")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getAppearance().equals("Demon")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getAppearance().equals("Robot")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			//pass
		}
		return i;
	}
	
	private void filterChampion(){
		//for(int i = 0; i < champions.size(); i++){
		int i = 0;
		while(i < champions.size()){
			switch(currentQuestionNumber){
			case 0:
				//1. Which Lane do you prefer?
				i = question1(i);
				break;
			case 1:
				//2.  Popular Champs VS Rare Champs?
				i = question2(i);
				break;
			case 2:
				//3. Which Damaging Style do you prefer?
				i = question3(i);
				break;
			case 3:
				//4. Would you like the appearance of your champion to be...
				i = question4(i);
				break;
			case 4:
				//5. When purchasing champions, I ...
				break;
			case 5:
				//6. Which Dealing Type do you prefer?
				break;
			case 6:
				//9. What kind of Active Skills do you think is the most important?
				break;
			case 7:
				//10. What kind of Passive Skills do you think is the most important?
				break;
			case 8:
				//8. Which level of champion difficulty do you prefer?
				break;
			case 9:
				//7. Which Style of main Attack Skill do you prefer?
				break;
			}
		}
		
		// Renew the linear layout with filtered champion list
		LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		scrollBar.removeAllViews();
		for (i = 0; i < temporary.size(); i++){
    		Button imgbtn = new Button(this);
    		//int resID = getApplicationContext().getResources().getIdentifier(temporary.get(i).getName().toLowerCase(), "drawable", "com.example.whosmychamp");
    		//imgbtn.setBackgroundResource(resID);
    		imgbtn.setText("asaa");
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				View popupView = getLayoutInflater().inflate(R.layout.result, null);
    				PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    				mPopupWindow.setAnimationStyle(-1);
    				mPopupWindow.showAsDropDown(v, 10, 10);    		 
    			}
    		});
    		scrollBar.addView(imgbtn);
		}
		
		// Renew champions list with filtered elements
		for(int j = 0; j < champions.size(); j++){
			history.add(champions.get(j));
		}
		champions = temporary;
		temporary = new ArrayList<Champion>();
	}

	private void nextQuestion(){
    	// print it on the screen
    	TextView question = (TextView)findViewById(R.id.questionView);
    	question.setText(questions.get(currentQuestionNumber * 7 + 0));
    	
    	RadioButton option1 = (RadioButton)findViewById(R.id.option1);
    	option1.setEnabled(true);
    	option1.setChecked(true);
    	if(questions.get(currentQuestionNumber * 7 + 1).equals("N/A")){
    		option1.setEnabled(false);
    	}
    	option1.setText(questions.get(currentQuestionNumber * 7 + 1));
    	
    	RadioButton option2 = (RadioButton)findViewById(R.id.option2);
    	option2.setEnabled(true);
    	option2.setChecked(false);
    	if(questions.get(currentQuestionNumber * 7 + 2).equals("N/A")){
    		option2.setEnabled(false);
    	}
    	option2.setText(questions.get(currentQuestionNumber * 7 + 2));
    	
    	RadioButton option3 = (RadioButton)findViewById(R.id.option3);
    	option3.setEnabled(true);
    	option3.setChecked(false);
    	if(questions.get(currentQuestionNumber * 7 + 3).equals("N/A")){
    		option3.setEnabled(false);
    	}
    	option3.setText(questions.get(currentQuestionNumber * 7 + 3));
    	
    	RadioButton option4 = (RadioButton)findViewById(R.id.option4);
    	option4.setEnabled(true);
    	option4.setChecked(false);
    	if(questions.get(currentQuestionNumber * 7 + 4).equals("N/A")){
    		option4.setEnabled(false);
    	}
    	option4.setText(questions.get(currentQuestionNumber * 7 + 4));
    	
    	RadioButton option5 = (RadioButton)findViewById(R.id.option5);
    	option5.setEnabled(true);
    	option5.setChecked(false);
    	if(questions.get(currentQuestionNumber * 7 + 5).equals("N/A")){
    		option5.setEnabled(false);
    	}
    	option5.setText(questions.get(currentQuestionNumber * 7 + 5));
    	
    	RadioButton option6 = (RadioButton)findViewById(R.id.option6);
    	option6.setEnabled(true);
    	option6.setChecked(false);
    	if(questions.get(currentQuestionNumber * 7 + 6).equals("N/A")){
    		option6.setEnabled(false);
    	}
    	option6.setText(questions.get(currentQuestionNumber * 7 + 6));
	}
}