package com.example.whosmychamp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class Result_List extends Activity {

	public static ArrayList<Champion> answers = Question.answers; // read the answers array list
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result_list);
	    
	    // TODO Auto-generated method stub
	}

}
