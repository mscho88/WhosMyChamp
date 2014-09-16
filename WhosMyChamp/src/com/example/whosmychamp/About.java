package com.example.whosmychamp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import jmentertainment.whosmychamp.R;

public class About extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.about);
	    
	    Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
	    TextView txt = (TextView) findViewById(R.id.aboutTxt);
	    txt.setTypeface(typeFace);
	    txt = (TextView) findViewById(R.id.textView1);
	    txt.setTypeface(typeFace);
	    
	    Button closebtn = (Button) findViewById(R.id.button1);
	    closebtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		        About.this.finish();

				//startActivity(new Intent(About.this, MainActivity.class));
			}
		});
	}
}
