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

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class HomeAutomationActivity extends Activity {

	public static final int mDuration = 250;
	public Typeface roboto_light,roboto_bold;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frameLayout = new FrameLayout(HomeAutomationActivity.this);
		frameLayout.setId(R.id.wrapper);
		setContentView(frameLayout);

		roboto_light = Typeface.createFromAsset(getAssets(),"RobotoLight.ttf");
		roboto_bold = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		HomeFragment homeFragment = HomeFragment.newInstance();
		fragmentTransaction.add(R.id.wrapper, homeFragment);
		fragmentTransaction.commitAllowingStateLoss();
	}
	
	public void deviceSelected(View view){
		String tag = (String) view.getTag();
		
		if(tag.equals("tv")){
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			RemoteFragment remoteFragment = RemoteFragment.newInstance();
			fragmentTransaction.replace(R.id.wrapper, remoteFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fragmentTransaction.commitAllowingStateLoss();
		}else{
			// TODO support more devices (e.g. PS3, webcam and stero)
			Toast.makeText(HomeAutomationActivity.this, "todo", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void remoteClicked(View view){
		String action = ((String)view.getTag());
		RemoteFragment.remoteAction(action);
	}
}