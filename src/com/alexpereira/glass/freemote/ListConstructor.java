package com.alexpereira.glass.freemote;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class ListConstructor extends MenuActivity{
	protected Activity act_parent;
	
	public void createListView(Activity parent){
		act_parent = parent;
		
    	mCards = new ArrayList<Card>();

        Card card;
        
        card = new Card(parent);
        card.setText("MENU");
        card.setFootnote("Navigate in the freebox homescreen");
        mCards.add(card);

        card = new Card(parent);
        card.setText("BROWSE CHANNELS");
        card.setFootnote("Use your glass to browser your channels");
        mCards.add(card);
        
        card = new Card(parent);
        card.setFootnote("Footer");
        mCards.add(card);        
        
        mCardScrollView = new CardScrollView(parent);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        
	
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.d("LIST CLICK",String.valueOf(position));
                
                switch(position){
	                case 0:
	            		Card card1 = new Card(act_parent);
	                    card1.setText("Navigate in your menu now");
	                    
	                    View card1View = card1.toView();
	                    act_parent.setContentView(card1View);
	                    
	            		mViewMode = ViewMode.MENU;
	
		              	if (mViewMode == ViewMode.MENU){
		              		jsonURL = baseRemoteURL+"&key=home";
		              		new LoadJSON().execute();
	              	}
            		break;
                	case 1:
                		Card card2 = new Card(act_parent);
                        card2.setText("Browse now");
                        
                        View card2View = card2.toView();
                        act_parent.setContentView(card2View);
                        
                		mViewMode = ViewMode.SWIPECHANNELS;

    	              	if (mViewMode == ViewMode.SWIPECHANNELS){
    	              		jsonURL = baseRemoteURL+"&key=ok";
    	              		new LoadJSON().execute();
    	              	}
                		break;
                	default:
                		break;
                }
            }
		});
        
        parent.setContentView(mCardScrollView);
	}
	
	private class ExampleCardScrollAdapter extends CardScrollAdapter {
        @Override
        public int findIdPosition(Object id) {
            return -1;
        }
        
        @Override
        public int findItemPosition(Object item) {
            return mCards.indexOf(item);
        }
        
        @Override
        public int getCount() {
            return mCards.size();
        }
        
        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).toView();
        } 
    }
}
