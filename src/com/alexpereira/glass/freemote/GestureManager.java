package com.alexpereira.glass.freemote;
/**
* Freebox Remote for Google Glass application
* you can control your freebox with your Glass ;)
*
* @author  Alexandre Pereira
* @version 1.0
* @since   2014-03-15 
*/

import android.app.Activity;

import com.google.android.glass.touchpad.Gesture;

public class GestureManager extends MainActivity{
	public GestureManager(){
	}
	
	public boolean parseGesture(Activity parent, Gesture gesture){
		if (gesture == Gesture.TAP) {
        	if (mViewMode == null) {
        		return false;
      	  	}
          	if (mViewMode == ViewMode.HOME) {
          		ListConstructor listConstructor = new ListConstructor();
          		listConstructor.createListView(parent);
          		
      		  	mViewMode = ViewMode.LIST;
          	}
          	
          	if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=ok";
          		new LoadJSON().execute();
          	}
          	
          	if (mViewMode == ViewMode.MENU){
        		jsonURL = baseRemoteURL+"&key=ok";
          		new LoadJSON().execute();
        	}
            return true;
        } else if (gesture == Gesture.TWO_TAP) {
        	if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=back";
          		new LoadJSON().execute();
          	}
            return true;
        } else if (gesture == Gesture.SWIPE_RIGHT) {
            if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=up";
          		new LoadJSON().execute();
        	}
        	
        	if (mViewMode == ViewMode.MENU){
        		jsonURL = baseRemoteURL+"&key=right";
          		new LoadJSON().execute();
        	}
        	
            return true;
        } else if (gesture == Gesture.TWO_SWIPE_RIGHT){
    		if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=up&long=true";
          		new LoadJSON().execute();
        	}
        	if (mViewMode == ViewMode.MENU){
        		jsonURL = baseRemoteURL+"&key=up";
          		new LoadJSON().execute();
        	}
            return true;
        } else if (gesture == Gesture.SWIPE_LEFT) {                	
        	if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=down";
          		new LoadJSON().execute();
          	}
        	
        	if (mViewMode == ViewMode.MENU){
        		jsonURL = baseRemoteURL+"&key=left";
          		new LoadJSON().execute();
        	}
        	
            return true;
        } else if (gesture == Gesture.TWO_SWIPE_LEFT){                	
        	if (mViewMode == ViewMode.SWIPECHANNELS) {
        		jsonURL = baseRemoteURL+"&key=down&long=true";
          		new LoadJSON().execute();
        	}
        	
        	if (mViewMode == ViewMode.MENU){
        		jsonURL = baseRemoteURL+"&key=down";
          		new LoadJSON().execute();
        	}
            return true;
        }
        
        return false;
	}
}
