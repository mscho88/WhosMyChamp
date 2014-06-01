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
import android.widget.TextView;

public class Question extends Activity {

	public ArrayList<String> questions = new ArrayList<String>();
	public ArrayList<String> heroList = new ArrayList<String>();
	public ArrayList<String> answers = new ArrayList<String>();
	public int currentQuestionNumber = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.question);
	    
	    if(MainActivity.isEnglish == true){
	    	// Read the xml question data file
	    	try{
	    		XmlPullParser customList = getResources().getXml(R.xml.questions_eng);
	    		while(customList.getEventType()!=XmlPullParser.END_DOCUMENT){
	    			if(customList.getEventType()==XmlPullParser.START_TAG){
	    				if(customList.getName().equals("option")){
	    					questions.add(customList.getAttributeValue(0));
	    				}
	    			}
	    			customList.next();
	    		}
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
					currentQuestionNumber++;
					//startActivity(new Intent(Question.this, Question.class));
					nextQuestion();
				}
			});
	    }else if(MainActivity.isEnglish == false){
	    	TextView question = (TextView)findViewById(R.id.questionView);
	    	question.setText("isEnglish is false");
	    }
	}
	
	public void nextQuestion(){
		if(currentQuestionNumber != 10){
	    	// print it on the screen
	    	TextView question = (TextView)findViewById(R.id.questionView);
	    	question.setText(questions.get(currentQuestionNumber * 7 + 0));
	    	
	    	TextView option1 = (TextView)findViewById(R.id.option1);
	    	option1.setText(questions.get(currentQuestionNumber * 7 + 1));
	    	
	    	TextView option2 = (TextView)findViewById(R.id.option2);
	    	option2.setText(questions.get(currentQuestionNumber * 7 + 2));
	    	
	    	TextView option3 = (TextView)findViewById(R.id.option3);
	    	option3.setText(questions.get(currentQuestionNumber * 7 + 3));
	    	
	    	TextView option4 = (TextView)findViewById(R.id.option4);
	    	option4.setText(questions.get(currentQuestionNumber * 7 + 4));
	    	
	    	TextView option5 = (TextView)findViewById(R.id.option5);
	    	option5.setText(questions.get(currentQuestionNumber * 7 + 5));
	    	
	    	TextView option6 = (TextView)findViewById(R.id.option6);
	    	option6.setText(questions.get(currentQuestionNumber * 7 + 6));
    	}
	}
}