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

import mnaylor.db.Db;
import note.Note;
import note.Stats;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

// Based on Android tutorial - http://developer.android.com/training/index.html
public class NewNoteActivity extends Activity {
	Db note_db;
	Note new_note;
	EditText subject;
	EditText content;
	EditText date;
    TextWatcher text_count = (new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			content = (EditText) findViewById(R.id.edit_content);
			TextView stat_display = (TextView) findViewById(R.id.content_stat);
			
			// update stats
			Stats stat_obj = new Stats(content.getText().toString());
			stat_display.setText("Character count = " + stat_obj.char_count + 
								"\n Word count = " + stat_obj.word_count);
			// save changes
			save_content();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub	
		}
    });
	
	@SuppressLint("New API")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
	
	@Override
	protected void onStart() {
		super.onStart();

        note_db = new Db(this);
        note_db.open();
        // Get the subject from the intent
        Intent intent = getIntent();
        String subject_raw = intent.getStringExtra(MainActivity.EXTRA_SUBJECT);
        String date_raw = intent.getStringExtra(MainActivity.EXTRA_DATE);
        String content_raw = intent.getStringExtra(MainActivity.EXTRA_CONTENTS);
        String id = intent.getStringExtra(MainActivity.EXTRA_ID);
        
        // if note not in db yet, it has no id
        if (id == null) {
        	new_note = new Note(subject_raw);
            save_to_db(new_note);
        }
        else {
        	int id_int = Integer.parseInt(id);
        	new_note = new Note(subject_raw, content_raw, date_raw, id_int);
        }
        	
        // Set the text view as the activity layout
        setContentView(R.layout.activity_display_note);

        // Display subject and date
        subject = (EditText) findViewById(R.id.subject);
        subject.setText(new_note.subject);
        subject.addTextChangedListener(text_count);
        date = (EditText) findViewById(R.id.date);
        date.setText(new_note.note_date);
        date.addTextChangedListener(text_count);
        
        // set up ability to get char/word counts for content		
        content = (EditText) findViewById(R.id.edit_content);
        content.addTextChangedListener(text_count);
        content.setText(new_note.contents);
	}
	
    @Override
    protected void onStop() {
    	super.onStop();
    	note_db.close();
    }
	
	public void save_to_db(Note new_note) {
		long new_id = note_db.insert_note(new_note.subject, new_note.note_date);
		new_note.set_id((int) new_id);
	}
    
	// called from TextWatcher
    public void save_content() {
    	subject = (EditText) findViewById(R.id.subject);
    	content = (EditText) findViewById(R.id.edit_content);
    	date = (EditText) findViewById(R.id.date);
    	String id = new_note.get_id().toString();
    	
    	note_db.open();
    	note_db.update_note(id, subject.getText().toString(),
    						content.getText().toString(), 
    						date.getText().toString());
    	note_db.close();
    }
    
    // called by pressing delete button
    public void delete_content(View view) {
    	String id = new_note.get_id().toString();
    	
    	note_db.open();
    	note_db.delete_note(id);
    	note_db.close();

    	finish();
    }
}
