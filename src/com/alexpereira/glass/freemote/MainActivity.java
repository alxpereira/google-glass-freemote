package com.alexpereira.glass.freemote;
/**
* Freebox Remote for Google Glass application
* you can control your freebox with your Glass ;)
*
* @author  Alexandre Pereira
* @version 1.0
* @since   2014-03-15 
*/

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
import com.alexpereira.glass.freemote.GestureManager;

/**
* MainActivity class called at the application initialization
*/
public class MainActivity extends Activity {
	/**
	* CardScrollView Object containing the cards in the app
	*/
	protected CardScrollView mCardScrollView;
	
	/**
	* mCards List 
	*/
	protected List<Card> mCards;
	
	/**
	* appContext - returning context used
	* @return context Application context
	*/
	protected Context appContext(){
		return getApplicationContext();
	}
	
	/**
	* ViewMode enum list, concerning the view type
	*/
	enum ViewMode {
		HOME,
		LIST,
		MENU,
		SWIPECHANNELS
	}
	
	/**
	* ViewMode mViewMode containing the current ViewMode from enum
	*/
	protected static ViewMode mViewMode;
	
	/**
	* GestureDetector object
	*/
	protected GestureDetector mGestureDetector;
	
	/**
	* freeboxCode string - code allowing your to access the remote api
	* @TODO : to be dynamically field.
	*/
	protected String freeboxCode = "84420033";
	
	/**
	* baseRemoteURL string - url of the freebox remote API
	*/
	protected String baseRemoteURL = "http://hd1.freebox.fr/pub/remote_control?code="+freeboxCode;
	
	/**
	* jsonURL string - current API url to call to activate an action
	*/
	protected String jsonURL;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // First Card to load
        Card card1 = new Card(this);
        card1.setImageLayout(Card.ImageLayout.FULL);
        card1.addImage(R.drawable.freebox);
        card1.setText(R.string.home_card);
        
        View card1View = card1.toView();
        setContentView(card1View);
        
        // The first mViewMode is HOME from enum
        mViewMode = ViewMode.HOME;
        
        // Getting the GestureDetector object for a further use
        mGestureDetector = createGestureDetector(this);
    }
    
    /**
     * Async JSON Loader
    */
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
    
    /**
     * Detect Gesture (swipe/tap etc...)
     * @param context Application context
     * 
     * @return gestureDetector Gesture Detector Glass object
    */
    private GestureDetector createGestureDetector(Context context) {
    	GestureDetector gestureDetector = new GestureDetector(context);
        //Create a base listener for generic gestures
        gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
            	Log.d("Geste", "Gesture Detected");
            	
            	GestureManager gMng = new GestureManager();
            	return gMng.parseGesture(MainActivity.this, gesture);
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

    /**
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
