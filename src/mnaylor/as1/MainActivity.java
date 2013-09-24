/** Based on Android tutorial published under Creative Commons
* http://developer.android.com/training/index.html
* 
* Published under GPL-V2
* http://www.gnu.org/licenses/gpl-2.0.html
*/

package mnaylor.as1;

import java.util.ArrayList;
import mnaylor.db.Constants;
import mnaylor.db.Db;
import note.Note;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	public final static String EXTRA_SUBJECT = "mnaylor.as1.SUBJECT";
	public final static String EXTRA_CONTENTS = "mnaylor.as1.CONTENTS";
	public final static String EXTRA_DATE = "mnaylor.as1.DATE";
	public final static String EXTRA_ID = "mnaylor.as1.ID";
	
	public Db note_db;
	NoteAdapter adapter;
	private ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
        note_db = new Db(this);
        note_db.open();
        
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText("");
        
        adapter = new NoteAdapter(this);
        this.setListAdapter(adapter);
        
        TextView num_entries = (TextView) findViewById(R.id.num_entries);
        num_entries.setText("Entry count = " + adapter.getCount());
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
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
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.top_words:
            	Intent intent = new Intent(this, TopWordsActivity.class);
            	startActivity(intent);
                return true;
            case R.id.word_diagram:
                // openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /** Called when the user clicks the new message button */
    public void newMessage(View view) {
    	Intent intent = new Intent(this, NewNoteActivity.class);
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String subject = editText.getText().toString();
    	intent.putExtra(EXTRA_SUBJECT, subject);
    	
    	startActivity(intent);
    }
    
    private class NoteAdapter extends BaseAdapter {
    	private LayoutInflater mInflater;
    	
    	public NoteAdapter(Context context) {
    		mInflater = LayoutInflater.from(context);
    		notes = new ArrayList<Note>();
    		getdata();
    	}
    	
    	@SuppressWarnings("deprecation")
		public void getdata(){
    		Cursor c = Db.get_notes();
    		startManagingCursor(c);
    	
    		if(c.moveToFirst()){
    			do{
    				String title =
    						c.getString(c.getColumnIndex(Constants.SUBJECT));
    				String content =
    						c.getString(c.getColumnIndex(Constants.CONTENT));
    				String date = c.getString(c.getColumnIndex(Constants.DATE));
    				int id = c.getInt(c.getColumnIndex(Constants.NOTE_ID));
    				
    				Note temp = new Note(title, content, date, id);
    				notes.add(temp);
    				
    			} while(c.moveToNext());
    		}
    	}

    	@Override
    	public int getCount() {return notes.size();}
    	
    	public Note getItem(int i) {return notes.get(i);}
    	
    	public long getItemId(int i) {return i;}
    	
    	public View getView(int arg0, View arg1, ViewGroup arg2) {
    		final ViewHolder holder;
    		View v = arg1;
    		if ((v == null) || (v.getTag() == null)) {
    				v = mInflater.inflate(R.layout.note_view, null);
    				holder = new ViewHolder();
    				holder.mSubject = (TextView)v.findViewById(R.id.note_subject);
    				holder.mDate = (TextView)v.findViewById(R.id.date_text);
    				v.setTag(holder);
    		} else {
    			holder = (ViewHolder) v.getTag();
    		}
    		holder.mnote = getItem(arg0);
    		holder.mSubject.setText(holder.mnote.subject);
    		holder.mDate.setText(holder.mnote.note_date);
    		v.setTag(holder);
    		return v;
    	}

    	public class ViewHolder {
    		Note mnote;
    		TextView mSubject;
    		TextView mDate;
    	}
    }
    
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Note selected;
    	selected = notes.get(position);
    	
    	Intent intent = new Intent(this, NewNoteActivity.class);
    	intent.putExtra(EXTRA_SUBJECT, selected.get_subject());
    	intent.putExtra(EXTRA_CONTENTS, selected.get_contents());
    	intent.putExtra(EXTRA_DATE,  selected.get_date());
    	intent.putExtra(EXTRA_ID, selected.get_id().toString());
    	startActivity(intent);
    }
}
