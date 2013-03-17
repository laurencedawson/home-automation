package com.laurencedawson.home_automation;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RemoteFragment extends Fragment {

	public static RemoteFragment newInstance(){
		RemoteFragment remoteFragment = new RemoteFragment();
		return remoteFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mRoot = inflater.inflate(R.layout.fragment_remote, null);
		
		Typeface roboto_light = ((HomeAutomationActivity)getActivity()).roboto_light;
		Typeface roboto_bold = ((HomeAutomationActivity)getActivity()).roboto_bold;

		return mRoot;
	}
	
	public static void remoteAction(final String action){
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
	
	/*
	 TouchPadView mTouchPadView = (TouchPadView) findViewById(R.id.touchpad);
//		mTouchPadView.setOnActionListener(new OnActionListener() {
//			@Override
//			public void onAction(String action) {
//				remoteAction(action);
//			}
//		});
	 */
	
}