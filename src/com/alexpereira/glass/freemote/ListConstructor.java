package com.alexpereira.glass.freemote;

import java.util.ArrayList;

import android.app.Activity;
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
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.home);
        card.setText("HOME");
        card.setFootnote("Navigate in the freebox homescreen");
        mCards.add(card);

        card = new Card(parent);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.channels);
        card.setText("BROWSE CHANNELS");
        card.setFootnote("Use your glass to browser your channels");
        mCards.add(card);
        
        card = new Card(parent);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(R.drawable.remote);
        card.setText("ON/OFF");
        card.setFootnote("Switch On/Off your Freebox");
        mCards.add(card);        
        
        mCardScrollView = new CardScrollView(parent);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        
	
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               switch(position){
	                case 0:
	            		Card card1 = new Card(act_parent);
	            		card1.setImageLayout(Card.ImageLayout.FULL);
	                    card1.addImage(R.drawable.home);
	                    card1.setText("Navigate in your menu now");
	                    card1.setFootnote("Swipe left or right with one finger and up and done with two fingers");
	                    
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
                        card2.setText("Browse now");
                        card2.setFootnote("Swipe to browser up and done, click to select");
                        
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
