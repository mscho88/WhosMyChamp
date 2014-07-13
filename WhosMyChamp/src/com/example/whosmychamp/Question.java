package com.example.whosmychamp;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	public PopupWindow curPopup = null;	
	
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
    	    customList = getResources().getXml(R.xml.questions_eng);
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
    							temp = champList.getAttributeValue(0).toString().split("-");
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
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setDamage_style(read);
    							j++;
    							break;
    						case 4:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setAppearance(read);
    							j++;
    							break;
    						case 5:
    							aChampion.setPrice(Integer.parseInt(champList.getAttributeValue(0)));
    							j++;
    							break;
    						case 6:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setType(read);
    							j++;
    							break;
    						case 7:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setActiveSkill(read);
    							j++;
    							break;
    						case 8:
    							temp = champList.getAttributeValue(0).split("-");
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
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setStyle(read);
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
    	
    	// champion list layout
    	LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		for (int i = 0; i < champions.size(); i++){
    		Button imgbtn = new Button(this);
    		int resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getName().toLowerCase(), "drawable", "com.example.whosmychamp");
    		imgbtn.setBackgroundResource(resID);
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				killPopup();
    				Point size = new Point();
    				getWindowManager().getDefaultDisplay().getSize(size);
    				    				
    				View popupView = getLayoutInflater().inflate(R.layout.result, null);
    				PopupWindow pop = new PopupWindow(popupView, (int) (size.x * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
    				pop.setAnimationStyle(-1);
    				pop.showAtLocation(v, 0, (int) (size.x * 0.1), (int) (size.y * 0.3));
    				
    				curPopup = pop;
    				/*
    				v.getBackground().
    				TextView txt = (TextView) findViewById(R.id.profile_pic);
    				txt = (TextView) v.findViewById(R.id.profile_name);
    				txt.setText("");
    				
    				txt = (TextView) v.findViewById(R.id.profile_pic);
    				
    				*/
    				Button close = (Button) popupView.findViewById(R.id.button1);
    				close.setOnClickListener(new OnClickListener(){
    					@Override
    					public void onClick(View arg0) {
    						killPopup();
    					}
    				});
    			}
    		});
    		scrollBar.addView(imgbtn);
		}
    	
		// Start with the first question
    	nextQuestion();
    	
    	// Set the listener at the next button
    	Button nextButton = (Button)findViewById(R.id.nextButton);
    	nextButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(currentQuestionNumber == numQuestions - 1){
					finishQuestion();
				}else{
					filterChampion();
					killPopup();
					currentQuestionNumber++;
					nextQuestion();
				}
			}
		});
	}
	
	private void killPopup(){
		if(curPopup != null){
			curPopup.dismiss();
			curPopup = null;
		}
	}
	
	private void finishQuestion(){
		//if(currentQuestionNumber == 9){
			Button btn = (Button) findViewById(R.id.nextButton);
			btn.setEnabled(false);
			TextView txt = (TextView) findViewById(R.id.questionView);
			txt.setText("All questions are completed. Now, you may choose a champion from above list. If no champion is left, then you can start again from the beginning.");
			RadioButton opt = (RadioButton) findViewById(R.id.option1);
			opt.setEnabled(false);
			opt.setText("");
			opt = (RadioButton) findViewById(R.id.option2);
			opt.setEnabled(false);
			opt.setText("");
			opt = (RadioButton) findViewById(R.id.option3);
			opt.setEnabled(false);
			opt.setText("");
			opt = (RadioButton) findViewById(R.id.option4);
			opt.setEnabled(false);
			opt.setText("");
			opt = (RadioButton) findViewById(R.id.option5);
			opt.setEnabled(false);
			opt.setText("");
			opt = (RadioButton) findViewById(R.id.option6);
			opt.setEnabled(false);
			opt.setText("");
		//}
	}
	
	private int question1(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getLane().contains("Top")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getLane().contains("Mid")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getLane().contains("Jungle")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getLane().contains("Bot(Marksman)")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option5)).isChecked()){
			if(champions.get(i).getLane().contains("Bot(Sup)")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
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
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question3(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getDamage_style().contains("Long")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getDamage_style().contains("Close")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question4(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getAppearance().contains("Human")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getAppearance().contains("Animal")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getAppearance().contains("Demon")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getAppearance().contains("Robot")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question5(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPrice() >= 1400){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPrice() <= 1400){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question6(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getType().contains("AP")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getType().contains("AD")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question7(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Stunning") ||
					champions.get(i).getActiveSkill().contains("Terror") ||
					champions.get(i).getActiveSkill().contains("Slowdown")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Healing") ||
					champions.get(i).getActiveSkill().contains("Barrier") ||
					champions.get(i).getActiveSkill().contains("Silence") ){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Hide") ||
					champions.get(i).getActiveSkill().contains("Escape")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Pull") ||
					champions.get(i).getActiveSkill().contains("Push") ||
					champions.get(i).getActiveSkill().contains("Daze")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question8(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Longlasting Damage")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Nonmana Resource")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Reducing Loss") ||
					champions.get(i).getPassiveSkill().contains("Lifesteal") ||
					champions.get(i).getPassiveSkill().contains("Revive")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Pet") ||
					champions.get(i).getPassiveSkill().contains("Trap")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question9(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getDifficulty() <= 3){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getDifficulty() <= 7){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getDifficulty() <= 10){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private int question10(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getStyle().contains("Split push")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getStyle().contains("Team fight")){
				temporary.add(champions.get(i));
				champions.remove(i);
			}else{
				i++;
			}
		}else{
			temporary.add(champions.get(i));
			champions.remove(i);
		}
		return i;
	}
	
	private void filterChampion(){
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
				i = question5(i);
				break;
			case 5:
				//6. Which Dealing Type do you prefer?
				i = question6(i);
				break;
			case 6:
				//9. What kind of Active Skills do you think is the most important?
				i = question7(i);
				break;
			case 7:
				//10. What kind of Passive Skills do you think is the most important?
				i = question8(i);
				break;
			case 8:
				//8. Which level of champion difficulty do you prefer?
				i = question9(i);
				break;
			case 9:
				//Which Fight Style do you give more weight to?
				i = question10(i);
				break;
			default:
				break;
			}
		}
		
		// Renew the linear layout with filtered champion list
		LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		scrollBar.removeAllViews();
		for (i = 0; i < temporary.size(); i++){
    		Button imgbtn = new Button(this);
    		int resID = getApplicationContext().getResources().getIdentifier(temporary.get(i).getName().toLowerCase(), "drawable", "com.example.whosmychamp");
    		imgbtn.setBackgroundResource(resID);
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				killPopup();
    				Point size = new Point();
    				getWindowManager().getDefaultDisplay().getSize(size);
    				    				
    				View popupView = getLayoutInflater().inflate(R.layout.result, null);
    				PopupWindow pop = new PopupWindow(popupView, (int) (size.x * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
    				pop.setAnimationStyle(-1);
    				pop.showAtLocation(v, 0, (int) (size.x * 0.1), (int) (size.y * 0.3));
    				curPopup = pop;
    				
    				Button close = (Button) popupView.findViewById(R.id.button1);
    				close.setOnClickListener(new OnClickListener(){
    					@Override
    					public void onClick(View arg0) {
    						killPopup();
    					}
    				});
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

	private void nextQuestionHelper(RadioButton option, int i){
		option.setEnabled(true);
		if(i == 1)
			option.setChecked(true);
		else
			option.setChecked(false);
		
    	if(questions.get(currentQuestionNumber * 7 + i).equals("N/A")){
    		option.setEnabled(false);
    		option.setText("");
    	}else{
    		option.setText(questions.get(currentQuestionNumber * 7 + i));
    	}
	}
	
	// Load the next question and set it up on the question and radio button.
	private void nextQuestion(){
    	// print it on the screen
    	TextView question = (TextView)findViewById(R.id.questionView);
    	question.setText(questions.get(currentQuestionNumber * 7 + 0));
    	
    	RadioButton option1 = (RadioButton)findViewById(R.id.option1);
    	nextQuestionHelper(option1, 1);
    	
    	RadioButton option2 = (RadioButton)findViewById(R.id.option2);
    	nextQuestionHelper(option2, 2);
    	
    	RadioButton option3 = (RadioButton)findViewById(R.id.option3);
    	nextQuestionHelper(option3, 3);

    	RadioButton option4 = (RadioButton)findViewById(R.id.option4);
    	nextQuestionHelper(option4, 4);
    	
    	RadioButton option5 = (RadioButton)findViewById(R.id.option5);
    	nextQuestionHelper(option5, 5);

    	RadioButton option6 = (RadioButton)findViewById(R.id.option6);
    	nextQuestionHelper(option6, 6);
	}
}