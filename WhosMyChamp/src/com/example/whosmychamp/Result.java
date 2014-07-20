package com.example.whosmychamp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Result extends Activity {

	private PopupWindow curpop;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result);
	    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
	    
	    for(int i = 0; i < Question.champions.size(); i++){
	    	LinearLayout line = new LinearLayout(this);
	    	
	    	Button btn = new Button(this);
	    	int resID = getApplicationContext().getResources().getIdentifier(Question.champions.get(i).getProfilePic(), "drawable", "com.example.whosmychamp");
	    	btn.setBackgroundResource(resID);
	    	btn.setId(Question.champions.get(i).getId());
	    	
	    	TextView txt = new TextView(this);
	    	txt.setText(Question.champions.get(i).getName());
	    	txt.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    	txt.setTextColor(Color.parseColor("#FFFFFF"));
	    	txt.setTextSize(25);
	    	Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
	    	txt.setTypeface(typeFace);
	    	txt.setId(Question.champions.get(i).getId());

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
		Champion chosen = null;
		for(int i = 0; i < Question.champions.size(); i++){
			if(Question.champions.get(i).getId() == v.getId()){
				chosen = Question.champions.get(i);
				break;
			}
		}

		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		    				
		View popupView = getLayoutInflater().inflate(R.layout.profile, null);
		PopupWindow pop = new PopupWindow(popupView, (int) (size.x * 0.8), (int) (size.x * 0.7));
		pop.setAnimationStyle(-1);
		pop.showAtLocation(v, 0, (int) (size.x * 0.1), (int) (size.y * 0.1));
		curpop = pop;
		
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
				curpop.dismiss();
			}
		});
	}
}
