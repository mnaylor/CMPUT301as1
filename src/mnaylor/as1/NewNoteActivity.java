/** Based on Android tutorial published under Creative Commons
* http://developer.android.com/training/index.html
* 
* Published under GPL-V2
* http://www.gnu.org/licenses/gpl-2.0.html
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
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        note_db = new Db(this);
        note_db.open();
        
        // Get the subject from the intent
        Intent intent = getIntent();
        String subject_raw = intent.getStringExtra(MainActivity.EXTRA_SUBJECT);
        String date_raw = intent.getStringExtra(MainActivity.EXTRA_DATE);
        String content_raw = intent.getStringExtra(MainActivity.EXTRA_CONTENTS);
        String id = intent.getStringExtra(MainActivity.EXTRA_ID);
        
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
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	// Show the Up button in the action bar.
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
	
	public void save_to_db(Note new_note) {
		long new_id = note_db.insert_note(new_note.subject, new_note.note_date);
		new_note.set_id((int) new_id);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
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
    
    public void delete_content(View view) {
    	String id = new_note.get_id().toString();
    	
    	note_db.open();
    	note_db.delete_note(id);
    	note_db.close();
    	
    	// go to main page
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
}
