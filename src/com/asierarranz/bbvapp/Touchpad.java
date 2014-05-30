/*

Copyright [2014] [ASIER ARRANZ - asierarranz@gmail.com - www.asierarranz.com - @asierarranz]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


*/
package com.asierarranz.bbvapp;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class Touchpad implements
		GestureDetector.BaseListener,
		GestureDetector.FingerListener,
		GestureDetector.ScrollListener {

	final float THRESHOLD = 240;
	float lastDisplacement = 0;
	MainActivity mActivity;

	public Touchpad(MainActivity pActivity) {
		mActivity = pActivity;
	}

	public boolean onGesture(Gesture gesture) {

		if (gesture == Gesture.TAP) {
			mActivity.comando("tap");
			return true;
		}
		else if (gesture == Gesture.TWO_TAP) {
			mActivity.comando("two_tap");
			return true;
		}
		else if (gesture == Gesture.SWIPE_DOWN) {
			mActivity.comando("swipe_down");
			return true;
		}
		/*else if (gesture == Gesture.SWIPE_RIGHT) {
			return true;
		}
		else if (gesture == Gesture.SWIPE_LEFT) {
			return true;
		}*/
		return false;
	}

	public void onFingerCountChanged(int previousCount, int currentCount) {
		lastDisplacement = 0;
	}

	public boolean onScroll(float displacement, float delta, float velocity) {
		
		if (Math.abs(displacement-lastDisplacement) > THRESHOLD) {
			if (displacement > lastDisplacement) {
				mActivity.comando("swipe_left");
			}
			else {
				mActivity.comando("swipe_right");
			}
			lastDisplacement = displacement;
			return true;
		}
		return false;
	}

}
