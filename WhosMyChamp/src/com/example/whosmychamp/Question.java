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
    			}
    		}
    	}
    	showChampions();

    	aRow = null;
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
		ArrayList<Champion> ans = new ArrayList<Champion>();
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
}