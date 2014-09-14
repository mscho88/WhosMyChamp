package com.example.whosmychamp;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity{

	private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		//Start the Splash Event
		startActivity(new Intent(this, Logo_Activity.class));

		setContentView(R.layout.fragment_main);

		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-3848140631863782/8505328953");
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.banner1);
		layout.addView(adView);
		
		AdRequest adRequest = new AdRequest.Builder()
	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
	    .build();

	// Start loading the ad in the background.
	adView.loadAd(adRequest);
	
		// English version button event handler
		Button button_eng = (Button)findViewById(R.id.button_eng);
		button_eng.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MainActivity.this, Question.class));
			}
		});
		
		Button buttonAbout = (Button)findViewById(R.id.button_about);
		buttonAbout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MainActivity.this, About.class));
			}
		});
		
		/*//Deleted the given code
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}

