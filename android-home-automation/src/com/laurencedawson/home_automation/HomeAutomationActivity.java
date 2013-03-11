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

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.laurencedawson.home_automation.TouchPadView.OnActionListener;

public class HomeAutomationActivity extends Activity {

	private Typeface roboto_light;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_automation);
		roboto_light = Typeface.createFromAsset(getAssets(),"RobotoLight.ttf");
		getActionBar().hide();

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
}
