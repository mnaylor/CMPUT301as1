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

package note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mnaylor.db.Db;
import android.database.Cursor;

public class WordFreq {
	public Db note_db;
	public HashMap<String, Integer> word_freq;

	public WordFreq(Db db) {
		this.note_db = db;
		word_freq = new HashMap<String, Integer>();
		set_word_freq();
	}
	
	public HashMap<String, Integer> getWord_freq() {
		return word_freq;
	}

	public void set_word_freq() {
		Cursor c = note_db.get_contents();
    	Integer freq;
    	
    	// populate Map with key=word, value=freq
		if(c.moveToFirst()){
			do {
				String[] split_contents = c.getString(0).toString().
											replaceAll("[^a-zA-Z_ _0-9]", "").
											split(" ");
				for (String word: split_contents) {
					if (word == "") break;
					freq = word_freq.get(word);
					if (freq == null)
						word_freq.put(word, 1);
					else word_freq.put(word, freq + 1);
				}
			} while(c.moveToNext());
		}
	}
	
	public ArrayList<ListFormat> map_to_arraylist(HashMap<String, Integer> map) {
		ArrayList<ListFormat> list = new ArrayList<ListFormat>();
		
		for (String entry: map.keySet()) {
			ListFormat item = new ListFormat(entry, map.get(entry));
			list.add(item);
		}
		Collections.sort(list);
		return list;
	}
	
	private class ListFormat implements Comparable {
		String word;
		Integer freq;
		
		public ListFormat(String word, Integer freq) {
			this.word = word;
			this.freq = freq;
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

