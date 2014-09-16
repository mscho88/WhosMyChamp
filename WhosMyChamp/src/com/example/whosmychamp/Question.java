package com.example.whosmychamp;

import java.io.IOException;
import java.util.ArrayList;
import jmentertainment.whosmychamp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
	public ArrayList<String> questions = new ArrayList<String>(); // Question list completed
	public ArrayList<Champion> champions = new ArrayList<Champion>(); // In progress
	public ArrayList<Champion> history = new ArrayList<Champion>(); // In progress
	
	public PopupWindow curPopup = null;
	public Champion chosen = null;
	
	Point screenSize;
	Typeface typeFace;
	
	private AdView adView;
	//private AdRequest adRequest ;
	//private AdView adView1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.question);
	    
	    // Read the font
	    typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
	    screenSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(screenSize);
		
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-3848140631863782/8505328953");
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.banner2);
		layout.addView(adView);
		
		AdRequest adRequest  = new AdRequest.Builder()
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
	    .build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
		
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
    							//temp = champList.getAttributeValue(0).toString().split("-");
    							//read = new ArrayList<String>();
    							//for(int k = 0; k < temp.length; k++){
    								//read.add(temp[k]);
    							//}
    							name = champList.getAttributeValue(0);
    							aChampion.setNickname(name);
    							j++;
    							break;
    						case 2:// need to save it in array
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setLane(read);
    							j++;
    							break;
    						case 3:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setStyle(read);
    							j++;
    							break;
    						case 4:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setRole(read);
    							j++;
    							break;
    						case 5:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setSkill(read);
    							j++;
    							break;
    						case 6:
    							temp = champList.getAttributeValue(0).toString().split("-");
    							read = new ArrayList<String>();
    							for(int k = 0; k < temp.length; k++){
    								read.add(temp[k]);
    							}
    							aChampion.setAppearance(read);
    							j++;
    							break;
    						case 7:
    							aChampion.setPrice(Integer.parseInt(champList.getAttributeValue(0)));
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
    	
    	OnClickListener checkListener = new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			if (((CheckBox) v).isChecked()) {
    				checkBoxOnClickOn((CheckBox) v);
    			}else {
    				checkBoxOnClickOff((CheckBox) v);
    			}
    		}
    	};
    	
    	TextView qt = (TextView)findViewById(R.id.questionText1);
    	qt.setTypeface(typeFace);
    	qt = (TextView)findViewById(R.id.questionText2);
    	qt.setTypeface(typeFace);
    	qt = (TextView)findViewById(R.id.questionText3);
    	qt.setTypeface(typeFace);
    	qt = (TextView)findViewById(R.id.questionText4);
    	qt.setTypeface(typeFace);
    	qt = (TextView)findViewById(R.id.questionText5);
    	qt.setTypeface(typeFace);
    	qt = (TextView)findViewById(R.id.questionText6);
    	qt.setTypeface(typeFace);
    	
    	CheckBox cb = (CheckBox)findViewById(R.id.laneBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.laneBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.laneBox3);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.laneBox4);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	cb = (CheckBox)findViewById(R.id.roleBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.roleBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.roleBox3);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.roleBox4);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.roleBox5);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.roleBox6);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	cb = (CheckBox)findViewById(R.id.skillBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox3);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox4);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox5);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox6);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox7);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox8);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox9);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox10);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox11);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox12);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox13);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox14);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox15);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.skillBox16);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	cb = (CheckBox)findViewById(R.id.appearanceBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.appearanceBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.appearanceBox3);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.appearanceBox4);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	cb = (CheckBox)findViewById(R.id.priceBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.priceBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.priceBox3);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.priceBox4);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.priceBox5);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	cb = (CheckBox)findViewById(R.id.apadBox1);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	cb = (CheckBox)findViewById(R.id.apadBox2);
    	cb.setTypeface(typeFace);
    	cb.setOnClickListener(checkListener);
    	
    	showChampions();
	}

	private void showChampions(){
		sortChampions();
		LinearLayout.LayoutParams WWParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 4.0f);
    	LinearLayout championScrollView = (LinearLayout)findViewById(R.id.championsScrollView);
    	LinearLayout aRow = new LinearLayout(this);
    	//LinearLayout container = null;//new LinearLayout(this);
        aRow.setOrientation(LinearLayout.HORIZONTAL);
        aRow.setLayoutParams(WWParam);
        
        //int num_list = 6;
        int num_list = (screenSize.x / 2 - 20) / 72 + 1;
    	int j = 1;
    	
    	championScrollView.removeAllViews();
    	
    	for(int i = 0; i < champions.size(); i++){
    		if (j <= num_list){
    			//container = new LinearLayout(this);
    			//WWParam.weight = 1.0f;
    			//aRow.setLayoutParams(WWParam);
    			//WWParam.weight = 4.0f;
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
	    		//container.addView(imgbtn);
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
			}else if(v.getText().toString().equals("AP") ||
					v.getText().toString().equals("AD")){
				if(champions.get(i).getStyle().contains(v.getText())){
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
				if(champions.get(i).getRole().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Airborne") ||
					v.getText().toString().equals("Blind") ||
					v.getText().toString().equals("Escape") ||
					v.getText().toString().equals("Terror") ||
					v.getText().toString().equals("Heal") ||
					v.getText().toString().equals("Fling")||
					v.getText().toString().equals("Knock Back")||
					v.getText().toString().equals("Observe")||
					v.getText().toString().equals("Shield")||
					v.getText().toString().equals("Silence")||
					v.getText().toString().equals("Slowdown")||
					v.getText().toString().equals("Restrict")||
					v.getText().toString().equals("Dominate")||
					v.getText().toString().equals("Stealth")||
					v.getText().toString().equals("Stun")||
					v.getText().toString().equals("Provoke")){
				if(champions.get(i).getSkill().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("Human") ||
					v.getText().toString().equals("Animal") ||
					v.getText().toString().equals("Demon") ||
					v.getText().toString().equals("Robot")){
				if(champions.get(i).getAppearance().contains(v.getText())){
					ans.add(champions.get(i));
				}else{
					history.add(champions.get(i));
				}
			}else if(v.getText().toString().equals("450") ||
					v.getText().toString().equals("1350") ||
					v.getText().toString().equals("3150") ||
					v.getText().toString().equals("4800") ||
					v.getText().toString().equals("6300")){
				if(champions.get(i).getPrice() == Integer.parseInt((String) v.getText())){
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
		ArrayList<Champion> hist = new ArrayList<Champion>();
		ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
		CheckBox cb = (CheckBox)findViewById(R.id.laneBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.laneBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.laneBox3);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.laneBox4);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.apadBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.apadBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox3);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox4);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox5);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.roleBox6);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox3);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox4);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox5);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox6);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox7);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox8);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox9);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox10);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox11);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox12);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox13);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox14);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox15);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.skillBox16);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.appearanceBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.appearanceBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.appearanceBox3);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.appearanceBox4);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.priceBox1);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.priceBox2);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.priceBox3);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.priceBox4);
		cbList.add(cb);
		cb = (CheckBox)findViewById(R.id.priceBox5);
		cbList.add(cb);
		
		for(int i = 0; i < cbList.size(); i++){
			if(cbList.get(i).isChecked()){
				if(cbList.get(i).getText().toString().equals("Top") ||
						cbList.get(i).getText().toString().equals("Mid") ||
						cbList.get(i).getText().toString().equals("Bottom") ||
						cbList.get(i).getText().toString().equals("Jungle")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getLane().contains(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}else if(cbList.get(i).getText().toString().equals("AP") ||
						cbList.get(i).getText().toString().equals("AD")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getStyle().contains(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}else if(cbList.get(i).getText().toString().equals("Fighter") ||
						cbList.get(i).getText().toString().equals("Tank") ||
						cbList.get(i).getText().toString().equals("Assassin") ||
						cbList.get(i).getText().toString().equals("Mage") ||
						cbList.get(i).getText().toString().equals("Marksman") ||
						cbList.get(i).getText().toString().equals("Support")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getRole().contains(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}else if(cbList.get(i).getText().toString().equals("Airborne") ||
						cbList.get(i).getText().toString().equals("Blind") ||
						cbList.get(i).getText().toString().equals("Escape") ||
						cbList.get(i).getText().toString().equals("Terror") ||
						cbList.get(i).getText().toString().equals("Heal") ||
						cbList.get(i).getText().toString().equals("Fling") ||
						cbList.get(i).getText().toString().equals("Knock Back") ||
						cbList.get(i).getText().toString().equals("Observe") ||
						cbList.get(i).getText().toString().equals("Shield") ||
						cbList.get(i).getText().toString().equals("Silence") ||
						cbList.get(i).getText().toString().equals("Slowdown") ||
						cbList.get(i).getText().toString().equals("Restrict") ||
						cbList.get(i).getText().toString().equals("Dominate") ||
						cbList.get(i).getText().toString().equals("Stealth") ||
						cbList.get(i).getText().toString().equals("Stun") ||
						cbList.get(i).getText().toString().equals("Provoke")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getSkill().contains(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}else if(cbList.get(i).getText().toString().equals("Human") ||
						cbList.get(i).getText().toString().equals("Animal") ||
						cbList.get(i).getText().toString().equals("Demon") ||
						cbList.get(i).getText().toString().equals("Robot")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getAppearance().contains(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}else if(cbList.get(i).getText().toString().equals("450") ||
						cbList.get(i).getText().toString().equals("1350") ||
						cbList.get(i).getText().toString().equals("3150") ||
						cbList.get(i).getText().toString().equals("4800") ||
						cbList.get(i).getText().toString().equals("6300")){
					for(int j = 0; j < history.size(); j++){
						if(history.get(j).getPrice() == Integer.parseInt(cbList.get(i).getText().toString())){
							ans.add(history.get(j));
						}else{
							hist.add(history.get(j));
						}
					}
					history = new ArrayList<Champion>();
				}
			}
			
			if(i != cbList.size() - 1){
				for(int j = 0; j < ans.size(); j++){
					history.add(ans.get(j));
				}
				ans = new ArrayList<Champion>();
			}else{
				for(int j = 0; j < history.size(); j++){
					champions.add(history.get(j));
				}
				for(int j = 0; j < hist.size(); j++){
					history.add(hist.get(j));
				}
			}
		}
		
		//champions = null;
		//champions = new ArrayList<Champion>();
		for(int i = 0; i < ans.size(); i++){
			champions.add(ans.get(i));
		}
		ans = null;
		history = new ArrayList<Champion>();
		for(int i = 0; i < hist.size(); i++){
			history.add(hist.get(i));
		}
		hist = null;
		showChampions();
	}
	
	private void killPopup(){
		if(curPopup != null){
			curPopup.dismiss();
			curPopup = null;
		}
	}
	
	private void sortChampions(){
		if(champions.size() > 1){
			Champion temp = null;
			Champion cur = null;//champions.get(0);
			
			for(int i = 0; i < champions.size() - 1; i++){
				//cur = champions.get(i);
				for(int j = i + 1; j < champions.size(); j++){
					if(champions.get(i).getId() > champions.get(j).getId()){
						temp = champions.get(i);
						champions.remove(i);
						champions.add(i, champions.get(j-1));
						champions.remove(j);
						champions.add(j, temp);
						
					}
				}
			}
		}
	}
	
	private void profilePopup(View v){
	    //setContentView(R.layout.profile);

		View popupView = getLayoutInflater().inflate(R.layout.profile, null);
		PopupWindow pop = new PopupWindow(popupView, (int) (screenSize.x * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
		pop.setAnimationStyle(-1);
		pop.showAtLocation(v, 0, (int) (screenSize.x * 0.1), (int) (screenSize.y * 0.3));
		curPopup = pop;
		 
		//Champion chosen = null;
		for(int i = 0; i < champions.size(); i++){
			if(champions.get(i).getId() == v.getId()){
				chosen = champions.get(i);
				break;
			}
		}

		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lolfont.ttf");
		ImageView profile_pic = (ImageView)popupView.findViewById(R.id.profile_pic);
		TextView profile_name = (TextView)popupView.findViewById(R.id.profile_name);
		TextView profile_nickname = (TextView)popupView.findViewById(R.id.profile_nickname);
		//TextView content = (TextView)popupView.findViewById(R.id.content);
		
		int resID = getApplicationContext().getResources().getIdentifier(chosen.getProfilePic(), "drawable", "com.example.whosmychamp");
		profile_pic.setBackgroundResource(resID);
		profile_name.setText(chosen.getName());
		profile_nickname.setText(chosen.getNickname());
		//content.setText("See more details ...." + screenSize.x);
	
		
	    profile_name.setTypeface(typeFace);
	    profile_nickname.setTypeface(typeFace);
	    //content.setTypeface(typeFace);
		
//AdView adView1 = new AdView(this.profilePopup(popupView))
		//AdView adView1 = new AdView(this);
		//adView1.setAdSize(AdSize.BANNER);
		//adView1.setAdUnitId("ca-app-pub-3848140631863782/8505328953");
		/*
		LinearLayout layout1 = (LinearLayout)popupView.findViewById(R.id.banner3);
		layout1.addView(adView);
		
		AdRequest adRequest = new AdRequest.Builder()
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
	    .build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
*/
		Button close = (Button) popupView.findViewById(R.id.button1);
		close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				killPopup();
			}
		});
		
		Button gotoweb = (Button) popupView.findViewById(R.id.button2);
		gotoweb.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://gameinfo.na.leagueoflegends.com/en/game-info/champions/" + chosen.getProfilePic()));
				startActivity(myIntent);
			}
		});
	}
	
	
	/*
	public static class AdFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
		    return inflater.inflate(R.layout.profile, container, false);
		}
		@Override
		public void onActivityCreated(Bundle bundle) {
		    super.onActivityCreated(bundle);
		    AdView mAdView = (AdView) getView().findViewById(R.id.banner);
		    AdRequest adRequest = new AdRequest.Builder().build();
		    mAdView.loadAd(adRequest);
		}
	}*/
}