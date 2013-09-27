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

import java.util.HashMap;

import mnaylor.db.Db;
import note.WordFreq;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class HistogramActivity extends Activity {
	public Db note_db;
	public HashMap<String, Integer> word_freq;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
    }
	
	@Override
    protected void onStart() {
    	super.onStart();
    	note_db = new Db(this);
    	note_db.open();
		WordFreq wf = new WordFreq(note_db);
    	
        word_freq = wf.getWord_freq();
        TextView word_cloud = (TextView) findViewById(R.id.wordCloud);
        word_cloud.setText(set_span_text(word_freq));
	}
	
    @Override
    protected void onStop() {
    	super.onStop();
    	note_db.close();
    }
	
	// Source: 
	// http://stackoverflow.com/questions/16060991/why-doesnt-my-text-show-up-with-style-when-using-spannablestringbuilder
	private SpannableStringBuilder set_span_text(HashMap<String, Integer> word_freq) {
		SpannableString sWord;
		SpannableStringBuilder sText = new SpannableStringBuilder("");
		Integer freq;
		
		for (String word: word_freq.keySet()) {
			freq = word_freq.get(word);
			sWord = new SpannableString(word + " ");
			sWord.setSpan(new RelativeSizeSpan(freq), 0, sWord.length(), 0);
			sText.append(sWord);
		}
		return sText;
	}	
}
