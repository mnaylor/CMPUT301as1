/** 
 * Android note taking application.
 * Copyright (C) 2013  Michelle Naylor
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
*/

package mnaylor.as1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import note.WordFreq;

import mnaylor.db.Db;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class TopWordsActivity extends ListActivity{
	public Db note_db;
	public HashMap<String, Integer> word_freq;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_words);
    }
	
	@Override
    protected void onStart() {
    	super.onStart();
    	ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
    	ArrayList<ListFormat> word_freq_list = new ArrayList<ListFormat>();
    	
    	note_db = new Db(this);
        note_db.open();
		WordFreq wf = new WordFreq(note_db);
    	
        word_freq = wf.getWord_freq();
        word_freq_list = map_to_arraylist(word_freq);
        
        // Source: 
        // http://stackoverflow.com/questions/17714580/android-listview-simple-adapter-item-repeating
        for (ListFormat lf: word_freq_list) {
            if (itemList.size() > 100) {
            	break;
            }
        	HashMap<String, String> itemMap = new HashMap<String, String>();
        	itemMap.put("word", lf.getWord());
        	itemMap.put("freq", lf.getFreq().toString());
            itemList.add(itemMap);
        }
        
        SimpleAdapter adapter = new SimpleAdapter(this, itemList,
                android.R.layout.simple_list_item_2, new String[] {"word",
                        "freq"}, new int[] { android.R.id.text1,
                        android.R.id.text2 });
        setListAdapter(adapter);
    }
	
    @Override
    protected void onStop() {
    	super.onStop();
    	note_db.close();
    }
	
	@SuppressWarnings("unchecked")
	public ArrayList<ListFormat> map_to_arraylist(HashMap<String, Integer> map) {
		ArrayList<ListFormat> list = new ArrayList<ListFormat>();
		
		for (String entry: map.keySet()) {
			ListFormat item = new ListFormat(entry, map.get(entry));
			list.add(item);
		}
		Collections.sort(list);
		Collections.reverse(list);
		return list;
	}
	
	private class ListFormat implements Comparable {
		String word;
		Integer freq;
		
		public ListFormat(String word, Integer freq) {
			this.word = word;
			this.freq = freq;
		}

		public String getWord() {
			return word;
		}

		public Integer getFreq() {
			return freq;
		}

		@Override
		public int compareTo(Object arg0) {
			if (arg0 instanceof ListFormat) {
				ListFormat lf = (ListFormat) arg0;
				return this.freq - lf.freq;
			}
			else { return 0; }
		}
	}
    
}	
