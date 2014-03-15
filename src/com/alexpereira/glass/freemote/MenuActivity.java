package com.alexpereira.glass.freemote;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
 
import com.google.android.glass.app.Card;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.widget.CardScrollView;

import com.alexpereira.glass.utils.JSONParser;

public class MenuActivity extends Activity {
	protected CardScrollView mCardScrollView;
	protected List<Card> mCards;
	
	protected Context appContext(){
		return getApplicationContext();
	}
	
	enum ViewMode {
		HOME,
		LIST,
		MENU,
		SWIPECHANNELS
	}
	
	protected static ViewMode mViewMode;
	protected GestureDetector mGestureDetector;
	protected String baseRemoteURL = "http://hd1.freebox.fr/pub/remote_control?code=84420033";
	protected String jsonURL;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Option 1 - View Activation (android style ^^)
        //setContentView(R.layout.activity_menu);
        
        // Option 2 - Card UI declaration and activation (glass UI rocks)
        // @link : https://developers.google.com/glass/develop/gdk/ui/theme-widgets#creating_glass-styled_cards
        
        Card card1 = new Card(this);
        card1.setText(R.string.home_card);
        
        View card1View = card1.toView();
        setContentView(card1View);
        
        //ListConstructor listConstructor = new ListConstructor();
  		//listConstructor.createListView(this);
        
        mViewMode = ViewMode.HOME;
        
        //GestureManager GestManager = new GestureManager();
        mGestureDetector = createGestureDetector(this);
    }
    
    public class LoadJSON extends AsyncTask<String, String, JSONObject> {
		@Override
	   	protected void onPreExecute() {
			super.onPreExecute();
	   	}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(jsonURL, "get");
			return json;
		}
		
		@Override
		protected void onPostExecute(JSONObject json) {
			Log.d("JSON", "json received");
		}
	}
    
    private GestureDetector createGestureDetector(Context context) {
    	GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
            	Log.d("Geste", "Gesture Detected");
                if (gesture == Gesture.TAP) {
	            	if (mViewMode == null) {
	            		return false;
	          	  	}
	              	if (mViewMode == ViewMode.HOME) {
	              		ListConstructor listConstructor = new ListConstructor();
	              		listConstructor.createListView(MenuActivity.this);
	              		
	          		  	mViewMode = ViewMode.LIST;
	              	}
	              	if (mViewMode == ViewMode.MENU){
                		jsonURL = baseRemoteURL+"&key=ok";
	              		new LoadJSON().execute();
                	}
                    return true;
                } else if (gesture == Gesture.TWO_TAP) {
                    // do something on two finger tap
                    return true;
                } else if (gesture == Gesture.SWIPE_UP) {
                	if (mViewMode == ViewMode.MENU){
                		jsonURL = baseRemoteURL+"&key=up";
	              		new LoadJSON().execute();
                	}
                    return true;
	            } else if (gesture == Gesture.SWIPE_DOWN) {
	            	if (mViewMode == ViewMode.MENU){
                		jsonURL = baseRemoteURL+"&key=down";
	              		new LoadJSON().execute();
                	}
	                return true;
	            }else if (gesture == Gesture.SWIPE_RIGHT) {
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
                    return true;
                }
                
                return false;
            }
        });
        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) {
              // do something on finger count changes
            }
        });
        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) {
				return false;
                // do something on scrolling
            }
        });
        return gestureDetector;
    }

    /*
     * Send generic motion events to the gesture detector
     */
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
}
