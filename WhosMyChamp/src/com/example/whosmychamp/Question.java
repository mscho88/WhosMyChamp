package com.example.whosmychamp;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Question extends Activity {

	// The number of questions that will be asked
	public final int numQuestions = 10;
	public int currentQuestionNumber = 0;
	
	public ArrayList<String> questions = new ArrayList<String>(); // Question list completed
	public static ArrayList<Champion> champList = new ArrayList<Champion>(); // In progress
	public static ArrayList<Champion> history = new ArrayList<Champion>(); // In progress
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.question);
	    
    	// Read the xml question data file
    	try{
    		XmlPullParser customList = null;
    	    if(MainActivity.isEnglish == true){
    	    	// Read English Questions
    	    	customList = getResources().getXml(R.xml.questions_eng);
    	    }else{
    	    	// Read Korean Questions
    	    	customList = getResources().getXml(R.xml.questions_kor);
    	    }
    		while(customList.getEventType() != XmlPullParser.END_DOCUMENT){
    			if(customList.getEventType() == XmlPullParser.START_TAG){
    				if(customList.getName().equals("option")){
    					questions.add(customList.getAttributeValue(0));
    				}
    			}
    			customList.next();
    		}
    		
    		// read hero xml data file
    		/*XmlPullParser champData = getResources().getXml(R.xml.champion_data);
    		while(champData.getEventType() != XmlPullParser.END_DOCUMENT){
    			if(champData.getEventType() == XmlPullParser.START_TAG){
    				if(champData.getName().equals("oaeijfgoaeif")){
    					// make hero object and add it in heroList
    					Champion aChampion = new Champion();
    					champList.add(aChampion);
    				}
    			}
    		}*/
    		
    	}catch(XmlPullParserException e){
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	nextQuestion();
    	
    	Button nextButton = (Button)findViewById(R.id.nextButton);
    	nextButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(currentQuestionNumber != numQuestions){
					// on each next click you need to filter the hero list and save the filtered hero objects to somewhere else
					currentQuestionNumber++;
					//startActivity(new Intent(Question.this, Question.class));
					nextQuestion();
					filterChampion();
				}else{
					startActivity(new Intent(Question.this, Result_List.class));
				}
			}
		});
	}
	
	protected void filterChampion() {
		/*for(int i = 0; i < champList.size(); i++){
			
		}*/
	}

	protected void nextQuestion(){
    	// print it on the screen
    	TextView question = (TextView)findViewById(R.id.questionView);
    	question.setText(questions.get(currentQuestionNumber * 7 + 0));
    	
    	RadioButton option1 = (RadioButton)findViewById(R.id.option1);
    	option1.setEnabled(true);
    	option1.setChecked(false);
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