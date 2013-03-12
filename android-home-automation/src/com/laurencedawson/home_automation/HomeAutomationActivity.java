/*
 * android-home-automation
 * Copyright (C) Copyright (C) 2013 Laurence Dawson <contact@laurencedawson.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.laurencedawson.home_automation;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.laurencedawson.home_automation.TouchPadView.OnActionListener;

public class HomeAutomationActivity extends Activity {

	public static final int mDuration = 250;
	private Typeface roboto_light;

	public static final String SHOW_LIGHTS = "show_lights";
	public static final String SHOW_TV = "show_tv";
	public static final String SHOW_STATS = "show_stats";

	private TextView mPowerTextView, mTemperatureTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_automation);
		roboto_light = Typeface.createFromAsset(getAssets(),"RobotoLight.ttf");

		((TextView)findViewById(R.id.lights_1)).setTypeface(roboto_light);
		((TextView)findViewById(R.id.lights_2)).setTypeface(roboto_light);
		((TextView)findViewById(R.id.lights_3)).setTypeface(roboto_light);

		TouchPadView mTouchPadView = (TouchPadView) findViewById(R.id.touchpad);
		mTouchPadView.setOnActionListener(new OnActionListener() {
			@Override
			public void onAction(String action) {
				remoteAction(action);
			}
		});

		Switch mTvSwitch = (Switch) findViewById(R.id.tv_switch);
		mTvSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				if(checked){
					findViewById(R.id.tv_buttons).setVisibility(View.VISIBLE);
					ObjectAnimator oa = ObjectAnimator.ofFloat(findViewById(R.id.tv_buttons), "alpha", 0.f,1.f);
					oa.setDuration(mDuration);
					oa.start();
				}else{
					findViewById(R.id.tv_buttons).setVisibility(View.GONE);
				}
				
				savePreference(SHOW_TV, checked);
			}
		});

		Switch mLightsSwitch = (Switch) findViewById(R.id.lights_switch);
		mLightsSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				if(checked){
					findViewById(R.id.light_buttons).setVisibility(View.VISIBLE);
					ObjectAnimator oa = ObjectAnimator.ofFloat(findViewById(R.id.light_buttons), "alpha", 0.f,1.f);
					oa.setDuration(mDuration);
					oa.start();
				}else{
					findViewById(R.id.light_buttons).setVisibility(View.GONE);
				}
				
				savePreference(SHOW_LIGHTS, checked);
			}
		});
		
		Switch mStatsSwitch = (Switch) findViewById(R.id.stats_switch);
		mStatsSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				if(checked){
					findViewById(R.id.stats_buttons).setVisibility(View.VISIBLE);
					ObjectAnimator oa = ObjectAnimator.ofFloat(findViewById(R.id.stats_buttons), "alpha", 0.f,1.f);
					oa.setDuration(mDuration);
					oa.start();
				}else{
					findViewById(R.id.stats_buttons).setVisibility(View.GONE);
				}
				
				savePreference(SHOW_STATS, checked);
			}
		});

		// Restore the previous views
		{
			SharedPreferences preferences = 
					PreferenceManager.getDefaultSharedPreferences(HomeAutomationActivity.this);

			{
				// Lights
				boolean mShowLights = preferences.getBoolean(SHOW_LIGHTS, true);
				((Switch)findViewById(R.id.lights_switch)).setChecked(mShowLights);
				findViewById(R.id.light_buttons).setVisibility(mShowLights?View.VISIBLE:View.GONE);
			}
			
			{
				// TV controls
				boolean mShowTv = preferences.getBoolean(SHOW_TV, true);
				((Switch)findViewById(R.id.tv_switch)).setChecked(mShowTv);
				findViewById(R.id.tv_buttons).setVisibility(mShowTv?View.VISIBLE:View.GONE);
			}
			
			{
				// Stats
				boolean mShowStats  = preferences.getBoolean(SHOW_STATS, true);
				((Switch)findViewById(R.id.stats_switch)).setChecked(mShowStats);
				findViewById(R.id.stats_buttons).setVisibility(mShowStats?View.VISIBLE:View.GONE);
			}
		}
		
		mTemperatureTextView = (TextView) findViewById(R.id.temperature_label);
		mTemperatureTextView.setTypeface(roboto_light);
		
		mPowerTextView = (TextView) findViewById(R.id.power_label);
		mPowerTextView.setTypeface(roboto_light);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void remoteClicked(View view){
		String action = ((String)view.getTag());
		remoteAction(action);
	}

	public void remoteAction(final String action){
		new Thread(){
			@Override
			public void run() {
				try{
					DefaultHttpClient client = new DefaultHttpClient();
					HttpGet request = new HttpGet("http://192.168.0.10/cgi-bin/remote.sh?"+action);
					client.execute(request);
					client.getConnectionManager().shutdown();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void savePreference(final String key, final boolean value){
		new Thread(){
			@Override
			public void run() {
				SharedPreferences preferences = 
						PreferenceManager.getDefaultSharedPreferences(HomeAutomationActivity.this);
				Editor edit = preferences.edit();
				edit.putBoolean(key, value);
				edit.commit();
			}
		}.start();
	}
}
