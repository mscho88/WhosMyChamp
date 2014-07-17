package com.example.whosmychamp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
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
	    	int resID = getApplicationContext().getResources().getIdentifier(Question.champions.get(i).getId(), "drawable", "com.example.whosmychamp");
	    	btn.setBackgroundResource(resID);
	    	btn.setId(i);
	    	
	    	TextView txt = new TextView(this);
	    	txt.setText(Question.champions.get(i).getName());
	    	txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    	txt.setTextColor(Color.parseColor("#FFFFFF"));
	    	txt.setTextSize(25);
	    	txt.setId(i);

	    	btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					profile(v);
				}    		
	    	});
	    	txt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					profile(arg0);
				}
	    		
	    	});
	    	
	    	line.addView(btn);
	    	line.addView(txt);
	    	layout.addView(line);
	    }
	}
	
	private void profile(View v){
		v.getId();
		////////////////
		//determine which button clicked
	}
}
