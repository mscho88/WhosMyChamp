package com.example.whosmychamp;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Question extends Activity {

	// The number of questions that will be asked
	//public final int numQuestions = 10;
	//public int currentQuestionNumber = 0;
	
	public ArrayList<String> questions = new ArrayList<String>(); // Question list completed
	public ArrayList<Champion> champions = new ArrayList<Champion>(); // In progress
	//public ArrayList<Champion> temporary = new ArrayList<Champion>(); // In progress
	public ArrayList<Champion> history = new ArrayList<Champion>(); // In progress
	
	public PopupWindow curPopup = null;
	
	Point screenSize;
	Typeface typeFace;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.question);
	    
	    // Read the font
	    typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
	    screenSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(screenSize);
	    	    
    	// Load xml question data file
    	try{
    		XmlPullParser customList = null;
    	    customList = getResources().getXml(R.xml.questions);
    	    while(customList.getEventType() != XmlPullParser.END_DOCUMENT){
    			if(customList.getEventType() == XmlPullParser.START_TAG){
    				if(customList.getName().equals("option")){
    					questions.add(customList.getAttributeValue(0));
    				}
    			}
    			customList.next();
    		}
    		
    		// Load champion xml data file
       		int i = 0, j = 0;
       		String[] temp;
       		ArrayList<String> read;
       		Champion aChampion = null;
       		
    		XmlPullParser champList = getResources().getXml(R.xml.champion_data);
    		while(champList.getEventType() != XmlPullParser.END_DOCUMENT){
    			i++;
    			if(champList.getEventType() == XmlPullParser.START_TAG){
    				if(champList.getName().equals("option")){
    					switch (j){
    						case 0:
    							aChampion = new Champion();
    							String name = champList.getAttributeValue(0);
    							aChampion.setName(name);
    							aChampion.setId(1000+i);
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
    	
    	LayoutParams WWParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	LayoutParams FWParam = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    	LayoutParams MWParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	LinearLayout.LayoutParams MW1Param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
    	
    	LinearLayout questionsLayout = (LinearLayout)findViewById(R.id.questionsLayout);
    	LinearLayout aRow = null;
    	boolean isQuestion = true;
    	// 1. questions
    	for(int i = 0; i < questions.size(); i++){
    		if(questions.get(i).toString().equals("$$$$")){
    			// initialize the entire row
    			aRow = new LinearLayout(this);
    			aRow.setLayoutParams(MWParam);
    			aRow.setOrientation(0);
    			aRow.setWeightSum(4);
    			isQuestion = true;
    		}else if(questions.get(i).toString().equals("$$$")){
    			// enter
    			questionsLayout.addView(aRow);
    			aRow = new LinearLayout(this);
    			aRow.setLayoutParams(MWParam);
    			aRow.setOrientation(0);
    			aRow.setWeightSum(4);
    		}else if(questions.get(i).toString().equals("$$")){
    			questionsLayout = null;
    			aRow = null;
    		}else{
    			if(isQuestion){
    				// Question
    				TextView questionView = new TextView(this);
    				questionView.setLayoutParams(WWParam);
    				questionView.setText(questions.get(i));
    				questionView.setTypeface(typeFace);
    				questionsLayout.addView(questionView);
	    	    	isQuestion = false;
    			}else{
    				// option
    				LinearLayout checkContainer = new LinearLayout(this);
	    	    	checkContainer.setLayoutParams(MW1Param);
	    	    	CheckBox check = new CheckBox(this);
	    		    check.setTypeface(typeFace);
	    	    	check.setId(i);
	    	    	check.setLayoutParams(FWParam);
	    	    	check.setText(questions.get(i).toString());
	    	    	check.setTextSize(10.0f);
	    	    	check.setOnClickListener(new OnClickListener() {
	    	    		@Override
	    	    		public void onClick(View v) {
	    	    			if (((CheckBox) v).isChecked()) {
	    	    				checkBoxOnClickOn((CheckBox) v);
	    	    			}else {
	    	    				checkBoxOnClickOff((CheckBox) v);
	    	    			}
	    	    		}
	    	    	});
	    	    	checkContainer.addView(check);
	    	    	aRow.addView(checkContainer);
	    	    	//check.setTextColor(Color.parseColor("#000000"));
    			}
    		}
    	}
    	showChampions();
/*
    	// 2. number of champions that will be in a row of scroll view.
    	int num_list = screenSize.x / 73;
    	int j = 1;
    	LinearLayout championScrollView = (LinearLayout)findViewById(R.id.championsScrollView);
    	aRow = new LinearLayout(this);
        aRow.setOrientation(LinearLayout.HORIZONTAL);
        aRow.setLayoutParams(WWParam);

    	for(int i = 0; i < champions.size(); i++){
    		if (j < num_list){
	    		Button imgbtn = new Button(this);
	    		int  resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getProfilePic(), "drawable", "com.example.whosmychamp");
	    		imgbtn.setBackgroundResource(resID);
	    		imgbtn.setId(champions.get(i).getId());
	    		imgbtn.setOnClickListener(new OnClickListener(){
	    			@Override
	    			public void onClick(View v){
	    				killPopup();
	    				profilePopup(v);
	    			}
	    		});
	    		aRow.addView(imgbtn);
	    		j++;
    		}else if(j == num_list){
    			championScrollView.addView(aRow);
    			aRow = new LinearLayout(this);
    			aRow.setOrientation(LinearLayout.HORIZONTAL);
    	        aRow.setLayoutParams(WWParam);
    			j = 1;
    		}
    	}
    	*/
    	aRow = null;
    	/*
    	// champion list layout
    	LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		for (int i = 0; i < champions.size(); i++){
    		Button imgbtn = new Button(this);
    		int resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getProfilePic(), "drawable", "com.example.whosmychamp");
    		imgbtn.setBackgroundResource(resID);
    		imgbtn.setId(champions.get(i).getId());
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				killPopup();
    				profilePopup(v);
    			}
    		});
    		scrollBar.addView(imgbtn);
		}*/
    	
		// Start with the first question
    	//nextQuestion();
    	/*
    	// Set the listener at the next button
    	Button nextButton = (Button)findViewById(R.id.nextButton);
    	nextButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(currentQuestionNumber == numQuestions - 1){
					finishQuestion();
					currentQuestionNumber++;
					// Renew champions list with filtered elements
					history.add(champions);
					champions = temporary;
					temporary = new ArrayList<Champion>();
					Button nextButton = (Button)findViewById(R.id.nextButton);
					nextButton.setText("See result");
					
				}else if(currentQuestionNumber == numQuestions){
					champions = history.get(currentQuestionNumber - 1);
					startActivity(new Intent(Question.this, Result.class));
				}else{
					Button backButton = (Button)findViewById(R.id.backButton);
					backButton.setEnabled(true);
					
					filterChampion();
					killPopup();
					currentQuestionNumber++;
					nextQuestion();
				}
			}
		});
    	
    	Button backButton = (Button) findViewById(R.id.backButton);
    	//backButton.setEnabled(true);
    	backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(currentQuestionNumber > 0){
					killPopup();
					currentQuestionNumber--;
					temporary = history.get(currentQuestionNumber);
					history.remove(currentQuestionNumber);
					nextQuestion();
					showChampions();	
					champions = temporary;
					temporary = new ArrayList<Champion>();
				}
				if(currentQuestionNumber == 0){
					Button backButton = (Button) findViewById(R.id.backButton);
					backButton.setEnabled(false);
				}
				if(currentQuestionNumber == numQuestions){
					Button nextButton = (Button) findViewById(R.id.nextButton);
					nextButton.setText("Next");
				}
			}
    	});*/
	}
	
	private void showChampions(){
    	// 2. number of champions that will be in a row of scroll view.
		
    	LayoutParams WWParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	LinearLayout championScrollView = (LinearLayout)findViewById(R.id.championsScrollView);
    	LinearLayout aRow = new LinearLayout(this);
        aRow.setOrientation(LinearLayout.HORIZONTAL);
        aRow.setLayoutParams(WWParam);
        
        int num_list = 6;
    	int j = 1;
    	
    	championScrollView.removeAllViews();
    	
    	for(int i = 0; i < champions.size(); i++){
    		if (j <= num_list){
	    		Button imgbtn = new Button(this);
	    		int  resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getProfilePic(), "drawable", "com.example.whosmychamp");
	    		imgbtn.setBackgroundResource(resID);
	    		imgbtn.setId(champions.get(i).getId());
	    		imgbtn.setOnClickListener(new OnClickListener(){
	    			@Override
	    			public void onClick(View v){
	    				killPopup();
	    				profilePopup(v);
	    			}
	    		});
	    		aRow.addView(imgbtn);
	    		j++;
    		}
    		if(j == num_list){
    			championScrollView.addView(aRow);
    			aRow = new LinearLayout(this);
    			aRow.setOrientation(LinearLayout.HORIZONTAL);
    	        aRow.setLayoutParams(WWParam);
    			j = 1;
    		}
    	}
		championScrollView.addView(aRow);
	}
	
	private void checkBoxOnClickOn(CheckBox v){
		ArrayList<Champion> ans = new ArrayList<Champion>(); // In progress
		for(int i = 0; i < champions.size(); i++){
			if(v.getText().toString().equals("Top") ||
					v.getText().toString().equals("Mid") ||
					v.getText().toString().equals("Jungle") ||
					v.getText().toString().equals("Bottom")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Fighter") ||
					v.getText().toString().equals("Tank") ||
					v.getText().toString().equals("Assassin") ||
					v.getText().toString().equals("Mage") ||
					v.getText().toString().equals("Marksman") ||
					v.getText().toString().equals("Support")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("skill1") ||
					v.getText().toString().equals("skill2") ||
					v.getText().toString().equals("skill3") ||
					v.getText().toString().equals("skill4") ||
					v.getText().toString().equals("skill5") ||
					v.getText().toString().equals("skill6")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Human-like") ||
					v.getText().toString().equals("Animal-like") ||
					v.getText().toString().equals("Demon-like") ||
					v.getText().toString().equals("Robot-like")){
				if(champions.get(i).getAppearance().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("450") ||
					v.getText().toString().equals("1250") ||
					v.getText().toString().equals("3150") ||
					v.getText().toString().equals("4800") ||
					v.getText().toString().equals("6300")){
				if(champions.get(i).getPrice() == Integer.parseInt((String) v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Ability Power") ||
					v.getText().toString().equals("Attack Damage")){
				if(champions.get(i).getDamage_style().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}
		}
		champions = null;
		champions = new ArrayList<Champion>();
		for(int i = 0; i < ans.size(); i++){
			champions.add(ans.get(i));
		}
		ans = null;
		showChampions();
	}

	private void checkBoxOnClickOff(CheckBox v){
		ArrayList<Champion> ans = new ArrayList<Champion>(); // In progress
		for(int i = 0; i < history.size(); i++){
			if(v.getText().toString().equals("Top") ||
					v.getText().toString().equals("Mid") ||
					v.getText().toString().equals("Jungle") ||
					v.getText().toString().equals("Bottom")){
				for(int j = 0; j < history.get(i).getLane().size(); j++){
					if(!(v.getText().toString().equals(history.get(i).getLane().get(j)))){
						if(v.getText().toString().equals("Top") ||
								v.getText().toString().equals("Mid") ||
								v.getText().toString().equals("Jungle") ||
								v.getText().toString().equals("Bottom")){
							ans.add(history.get(i));
							j = history.get(i).getLane().size();
						}
					}
				}
				/*
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}*/
				
			}else if(v.getText().toString().equals("Fighter") ||
					v.getText().toString().equals("Tank") ||
					v.getText().toString().equals("Assassin") ||
					v.getText().toString().equals("Mage") ||
					v.getText().toString().equals("Marksman") ||
					v.getText().toString().equals("Support")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("skill1") ||
					v.getText().toString().equals("skill2") ||
					v.getText().toString().equals("skill3") ||
					v.getText().toString().equals("skill4") ||
					v.getText().toString().equals("skill5") ||
					v.getText().toString().equals("skill6")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Human-like") ||
					v.getText().toString().equals("Animal-like") ||
					v.getText().toString().equals("Demon-like") ||
					v.getText().toString().equals("Robot-like")){
				if(champions.get(i).getAppearance().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("450") ||
					v.getText().toString().equals("1250") ||
					v.getText().toString().equals("3150") ||
					v.getText().toString().equals("4800") ||
					v.getText().toString().equals("6300")){
				if(champions.get(i).getPrice() == Integer.parseInt((String) v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Ability Power") ||
					v.getText().toString().equals("Attack Damage")){
				if(champions.get(i).getDamage_style().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}
		}
		
		
		/*
		for(int i = 0; i < champions.size(); i++){
			if(v.getText().toString().equals("Top") ||
					v.getText().toString().equals("Mid") ||
					v.getText().toString().equals("Jungle") ||
					v.getText().toString().equals("Bottom")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
				for(int j = 0; j < history.size(); j++){
					for(int k = 0; k < history.get(i).getLane().size(); k++){
						if()
						ans.add(history.get(i));
					}
				}
			}else if(v.getText().toString().equals("Fighter") ||
					v.getText().toString().equals("Tank") ||
					v.getText().toString().equals("Assassin") ||
					v.getText().toString().equals("Mage") ||
					v.getText().toString().equals("Marksman") ||
					v.getText().toString().equals("Support")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("skill1") ||
					v.getText().toString().equals("skill2") ||
					v.getText().toString().equals("skill3") ||
					v.getText().toString().equals("skill4") ||
					v.getText().toString().equals("skill5") ||
					v.getText().toString().equals("skill6")){
				if(champions.get(i).getLane().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Human-like") ||
					v.getText().toString().equals("Animal-like") ||
					v.getText().toString().equals("Demon-like") ||
					v.getText().toString().equals("Robot-like")){
				if(champions.get(i).getAppearance().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("450") ||
					v.getText().toString().equals("1250") ||
					v.getText().toString().equals("3150") ||
					v.getText().toString().equals("4800") ||
					v.getText().toString().equals("6300")){
				if(champions.get(i).getPrice() == Integer.parseInt((String) v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Ability Power") ||
					v.getText().toString().equals("Attack Damage")){
				if(champions.get(i).getDamage_style().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}
		}*/
		//champions = null;
		//champions = new ArrayList<Champion>();
		for(int i = 0; i < ans.size(); i++){
			champions.add(ans.get(i));
		}
		ans = null;
		showChampions();
	}
	
	private void killPopup(){
		if(curPopup != null){
			curPopup.dismiss();
			curPopup = null;
		}
	}
	
	private void profilePopup(View v){
		View popupView = getLayoutInflater().inflate(R.layout.profile, null);
		PopupWindow pop = new PopupWindow(popupView, (int) (screenSize.x * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(-1);
		pop.showAtLocation(v, 0, (int) (screenSize.x * 0.1), (int) (screenSize.y * 0.3));
		curPopup = pop;
		 
		Champion chosen = null;
		for(int i = 0; i < champions.size(); i++){
			if(champions.get(i).getId() == v.getId()){
				chosen = champions.get(i);
				break;
			}
		}

		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
		ImageView profile_pic = (ImageView)popupView.findViewById(R.id.profile_pic);
		TextView profile_name = (TextView)popupView.findViewById(R.id.profile_name);
		TextView content = (TextView)popupView.findViewById(R.id.content);
		
		int resID = getApplicationContext().getResources().getIdentifier(chosen.getProfilePic(), "drawable", "com.example.whosmychamp");
		profile_pic.setBackgroundResource(resID);
		profile_name.setText(chosen.getName());
		content.setText("hahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahaha");
		
	    profile_name.setTypeface(typeFace);
	    content.setTypeface(typeFace);
		
		Button close = (Button) popupView.findViewById(R.id.button1);
		close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				killPopup();
			}
		});
	}
	 /*
	private void finishQuestion(){
		//if(currentQuestionNumber == 9){
			TextView txt = (TextView) findViewById(R.id.questionView);
			txt.setText("All questions are completed. See result to see more details.");
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
*//*
	private void showChampions(){
		// Renew the linear layout with filtered champion list
		LinearLayout scrollBar = (LinearLayout)findViewById(R.id.listContainer);
		scrollBar.removeAllViews();
		for (int i = 0; i < champions.size(); i++){
    		Button imgbtn = new Button(this);
    		int resID = getApplicationContext().getResources().getIdentifier(champions.get(i).getProfilePic(), "drawable", "com.example.whosmychamp");
    		imgbtn.setBackgroundResource(resID);
    		int a = champions.get(i).getId();
    		int b = imgbtn.getId();
    		imgbtn.setId(champions.get(i).getId());
    		imgbtn.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				killPopup();
    				profilePopup(v);
    			}
    		});
    		a = champions.get(i).getId();
    		b = imgbtn.getId();
    		scrollBar.addView(imgbtn);
		}
	}*/
	/*
	private void question1(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getLane().contains("Top")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getLane().contains("Mid")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getLane().contains("Jungle")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getLane().contains("Bot(Marksman)")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option5)).isChecked()){
			if(champions.get(i).getLane().contains("Bot(Sup)")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question2(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPopularity() == 1){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPopularity() == 0){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question3(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getDamage_style().contains("Long")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getDamage_style().contains("Close")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question4(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getAppearance().contains("Human")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getAppearance().contains("Animal")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getAppearance().contains("Demon")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getAppearance().contains("Robot")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question5(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPrice() >= 1400){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPrice() <= 1400){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question6(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getType().contains("AP")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getType().contains("AD")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question7(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Stunning") ||
					champions.get(i).getActiveSkill().contains("Terror") ||
					champions.get(i).getActiveSkill().contains("Slowdown")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Healing") ||
					champions.get(i).getActiveSkill().contains("Barrier") ||
					champions.get(i).getActiveSkill().contains("Silence") ){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Hide") ||
					champions.get(i).getActiveSkill().contains("Escape")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getActiveSkill().contains("Pull") ||
					champions.get(i).getActiveSkill().contains("Push") ||
					champions.get(i).getActiveSkill().contains("Daze")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question8(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Longlasting Damage")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Nonmana Resource")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Reducing Loss") ||
					champions.get(i).getPassiveSkill().contains("Lifesteal") ||
					champions.get(i).getPassiveSkill().contains("Revive")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option4)).isChecked()){
			if(champions.get(i).getPassiveSkill().contains("Pet") ||
					champions.get(i).getPassiveSkill().contains("Trap")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question9(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getDifficulty() <= 3){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getDifficulty() <= 7){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option3)).isChecked()){
			if(champions.get(i).getDifficulty() <= 10){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	
	private void question10(int i){
		if(((RadioButton) findViewById(R.id.option1)).isChecked()){
			if(champions.get(i).getStyle().contains("Split push")){
				temporary.add(champions.get(i));
			}
		}else if(((RadioButton) findViewById(R.id.option2)).isChecked()){
			if(champions.get(i).getStyle().contains("Team fight")){
				temporary.add(champions.get(i));
			}
		}else{
			temporary.add(champions.get(i));
		}
	}
	*/
	/*
	private void filterChampion(){
		int i = 0;
		while(i < champions.size()){
			switch(currentQuestionNumber){
			case 0:
				//1. Which Lane do you prefer?
				question1(i);
				i++;
				break;
			case 1:
				//2.  Popular Champs VS Rare Champs?
				question2(i);
				i++;
				break;
			case 2:
				//3. Which Damaging Style do you prefer?
				question3(i);
				i++;
				break;
			case 3:
				//4. Would you like the appearance of your champion to be...
				question4(i);
				i++;
				break;
			case 4:
				//5. When purchasing champions, I ...
				question5(i);
				i++;
				break;
			case 5:
				//6. Which Dealing Type do you prefer?
				question6(i);
				i++;
				break;
			case 6:
				//9. What kind of Active Skills do you think is the most important?
				question7(i);
				i++;
				break;
			case 7:
				//10. What kind of Passive Skills do you think is the most important?
				question8(i);
				i++;
				break;
			case 8:
				//8. Which level of champion difficulty do you prefer?
				question9(i);
				i++;
				break;
			case 9:
				//Which Fight Style do you give more weight to?
				question10(i);
				i++;
				break;
			default:
				break;
			}
		}
		
		showChampions();
		
		// Renew champions list with filtered elements
		history.add(champions);
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
    	
	}*/
}