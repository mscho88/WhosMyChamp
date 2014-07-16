package com.example.whosmychamp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Result extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result);
	    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	    
	    for(int i = 0; i < Question.champions.size(); i++){
	    	LinearLayout line = new LinearLayout(this);
	    	
	    	Button btn = new Button(this);
	    	int resID = getApplicationContext().getResources().getIdentifier(Question.champions.get(i).getName().toLowerCase(), "drawable", "com.example.whosmychamp");
	    	btn.setBackgroundResource(resID);
	    	
	    	TextView txt = new TextView(this);
	    	txt.setText(Question.champions.get(i).getName());

	    	line.addView(btn);
	    	line.addView(txt);
	    	layout.addView(line);
	    }
	}

}
