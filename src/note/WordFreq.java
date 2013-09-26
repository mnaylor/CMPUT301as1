package note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mnaylor.db.Db;
import android.database.Cursor;

public class WordFreq {
	public Db note_db;
	public Map<String, Integer> word_freq;

	public WordFreq(Db db) {
		this.note_db = db;
		word_freq = new HashMap<String, Integer>();
		set_word_freq();
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

	public ArrayList map_to_arraylist(HashMap<String, Integer> map) {
		ArrayList list = new ArrayList<ListFormat>();
		
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

