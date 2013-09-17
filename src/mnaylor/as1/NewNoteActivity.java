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
import android.widget.EditText;

public class NewNoteActivity extends Activity {
	Db note_db;
	
	@SuppressLint("New API")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get the subject from the intent
        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Note new_note = new Note(subject);

        // Set the text view as the activity layout
        setContentView(R.layout.activity_display_note);

        // Display subject and date
        EditText subject_line = (EditText) findViewById(R.id.subject);
        subject_line.setText(new_note.subject);
        EditText date_line = (EditText) findViewById(R.id.date);
        date_line.setText(new_note.note_date);
     
        // TODO: add note to database
        note_db = new Db(this);
        note_db.open();
        
        save_to_db(new_note);
        
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

}
