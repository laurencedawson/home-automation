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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class TouchPadView extends View {

	private int mBorderWidth;
	private Paint mPaint;
	private GestureDetector mGestureDetector;
	private int mSensitivity = 50;
	private OnActionListener mActionListener;

	public TouchPadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mBorderWidth = context.getResources().getDimensionPixelOffset(R.dimen.border_width);
		mPaint = new Paint();
		mPaint.setColor(0xff373939);

		// Setup the GestureDetector
		mGestureDetector = new GestureDetector(context, new TouchPadGestureListener());
		
		// Setup the sensitivity for the swipes
		mSensitivity = 
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Top
		canvas.drawRect(0, 0, getWidth(), mBorderWidth, mPaint);

		// Bottom
		canvas.drawRect(0, getHeight()-mBorderWidth, getWidth(), getHeight(), mPaint);

		// Left
		canvas.drawRect(0, 0, mBorderWidth, getHeight(), mPaint);

		// right
		canvas.drawRect(getWidth()-mBorderWidth, 0, getWidth(), getHeight(), mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent( event );
		return true;
	}

	private class TouchPadGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if(mActionListener!=null){
				mActionListener.onAction("OK");
			}
			return true;
		}


		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			// Detect left swipe
			if((e1.getX() - e2.getX()) > mSensitivity){
				if(mActionListener!=null){
					mActionListener.onAction("LEFT");
				}
			}

			// Detect right swipe
			else if((e2.getX() - e1.getX()) > mSensitivity){
				if(mActionListener!=null){
					mActionListener.onAction("RIGHT");
				}
			}

			// Detect up swipe
			else if((e1.getY() - e2.getY()) > mSensitivity){
				if(mActionListener!=null){
					mActionListener.onAction("UP");
				}
			}

			// Detect down swipe
			else if((e2.getY() - e1.getY()) > mSensitivity){
				if(mActionListener!=null){
					mActionListener.onAction("DOWN");
				}
			}

			return true;
		}
	}
	
	public interface OnActionListener {
		void onAction(String action);
	}
	
	public void setOnActionListener(OnActionListener flingListener){
		this.mActionListener = flingListener;
	}
}