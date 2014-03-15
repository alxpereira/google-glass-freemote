package com.alexpereira.glass.freemote;
/**
* Freebox Remote for Google Glass application
* you can control your freebox with your Glass ;)
*
* @author  Alexandre Pereira
* @version 1.0
* @since   2014-03-15 
*/

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

/**
* ListConstructor class for the scroll list constructions
*/
public class ListConstructor extends MainActivity{
	/**
	* act_parent Activity - getting the parent Activity
	*/
	protected Activity act_parent;
	
	/**
	* FreeCardScrollAdapter - will contains the cards and its getters 
	*/
	private class FreeCardScrollAdapter extends CardScrollAdapter {
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
	
	/**
	* createListView - creating the main list view after the homepage
	* @param parent Parent Activity
	*/
	public void createListView(Activity parent){
		act_parent = parent;
		
    	mCards = new ArrayList<Card>();

        Card card;
        
        // Home Browser card
        card = new Card(parent);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.home);
        card.setText(R.string.home_ttl);
        card.setFootnote(R.string.home_foot);
        mCards.add(card);

        // Channels Browser card
        card = new Card(parent);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.channels);
        card.setText(R.string.channels_ttl);
        card.setFootnote(R.string.channels_foot);
        mCards.add(card);
        
        // On/Off action card
        card = new Card(parent);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.remote);
        card.setText(R.string.onoff_ttl);
        card.setFootnote(R.string.onoff_foot);
        mCards.add(card);        
        
        mCardScrollView = new CardScrollView(parent);
        FreeCardScrollAdapter adapter = new FreeCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        
    	// Set mCardScrollView listener (onclick)
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               listViewClick(position);
            }
		});
        
        parent.setContentView(mCardScrollView);
	}
	
	/**
	* listViewClick - actions after the list click
	* @param position Integer containing the position of the item clicked
	*/
	private void listViewClick(int position){
		switch(position){
	        case 0:
	    		Card card1 = new Card(act_parent);
	    		card1.setImageLayout(Card.ImageLayout.FULL);
	            card1.addImage(R.drawable.home);
	            
	            card1.setText(R.string.home_inner_ttl);
	            card1.setFootnote(R.string.home_inner_ttl);
	            
	            View card1View = card1.toView();
	            act_parent.setContentView(card1View);
	            
	    		mViewMode = ViewMode.MENU;
	
	      		jsonURL = baseRemoteURL+"&key=home";
	      		new LoadJSON().execute();
	
	      		break;
	    	case 1:
	    		Card card2 = new Card(act_parent);
	    		card2.setImageLayout(Card.ImageLayout.FULL);
	            card2.addImage(R.drawable.channels);
	            card2.setText(R.string.channels_inner_ttl);
	            card2.setFootnote(R.string.channels_inner_foot);
	            
	            View card2View = card2.toView();
	            act_parent.setContentView(card2View);
	            
	    		mViewMode = ViewMode.SWIPECHANNELS;
	
	          	if (mViewMode == ViewMode.SWIPECHANNELS){
	          		jsonURL = baseRemoteURL+"&key=ok";
	          		new LoadJSON().execute();
	          	}
	    		break;
	    	case 2:
	    		jsonURL = baseRemoteURL+"&key=power";
	      		new LoadJSON().execute();
	    		break;
	    	default:
	    		break;
	    }
	}
}
