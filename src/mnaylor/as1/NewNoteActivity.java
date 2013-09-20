/** Based on Android tutorial published under Creative Commons
* http://developer.android.com/training/index.html
* 
* Published under GPL-V2
* http://www.gnu.org/licenses/gpl-2.0.html
*/

package mnaylor.as1;

import mnaylor.db.Db;
import note.Note;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewNoteActivity extends Activity {
	Db note_db;
	Note new_note;
	
	@SuppressLint("New API")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note_db = new Db(this);
        note_db.open();
        
        // Get the subject from the intent
        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.EXTRA_SUBJECT);
        String date = intent.getStringExtra(MainActivity.EXTRA_DATE);
        String content = intent.getStringExtra(MainActivity.EXTRA_CONTENTS);
        String id = intent.getStringExtra(MainActivity.EXTRA_ID);
        
        if (id == null) {
        	new_note = new Note(subject);
            save_to_db(new_note);
            note_db.close();
        }
        else {
        	int id_int = Integer.parseInt(id);
        	new_note = new Note(subject, content, date, id_int);
        }
        	

        // Set the text view as the activity layout
        setContentView(R.layout.activity_display_note);

        // Display subject and date
        EditText subject_line = (EditText) findViewById(R.id.subject);
        subject_line.setText(new_note.subject);
        EditText date_line = (EditText) findViewById(R.id.date);
        date_line.setText(new_note.note_date);
        EditText content_line = (EditText) findViewById(R.id.edit_content);
        content_line.setText(new_note.contents);
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	// Show the Up button in the action bar.
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
	
	public void save_to_db(Note new_note) {
		note_db.insert_note(new_note.subject, new_note.note_date);
		
		note_db.close();
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
    
    public void save_content(View view) {
    	EditText subject = (EditText) findViewById(R.id.subject);
    	EditText content = (EditText) findViewById(R.id.edit_content);
    	EditText date = (EditText) findViewById(R.id.date);
    	String id = new_note.get_id().toString();
    	
    	System.out.println("id = " + id);
    	System.out.println("subject = " + subject.getText().toString());
    	System.out.println("content = " + content.getText().toString());
    	System.out.println("date = " + date.getText());
    	note_db.open();
    	note_db.update_note(id, subject.getText().toString(),
    						content.getText().toString(), 
    						date.getText().toString());
    	note_db.close();
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    
    public void delete_content(View view) {
    	String id = new_note.get_id().toString();
    	
    	note_db.open();
    	note_db.delete_note(id);
    	note_db.close();
    	
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
}
