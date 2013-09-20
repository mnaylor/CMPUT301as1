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
import android.widget.TextView;

public class MainActivity extends ListActivity {

	public final static String EXTRA_MESSAGE = "mnaylor.as1.MESSAGE";
	public Db note_db;
	NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        note_db = new Db(this);
        note_db.open();
        
        adapter = new NoteAdapter(this);
        this.setListAdapter(adapter);
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            case R.id.action_search:
                // openSearch();
                return true;
            case R.id.action_settings:
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
    	intent.putExtra(EXTRA_MESSAGE, subject);
    	startActivity(intent);
    }
    
    private class NoteAdapter extends BaseAdapter {
    	private LayoutInflater mInflater;
    	private ArrayList<Note> notes;
    	
    	public NoteAdapter(Context context) {
    		mInflater = LayoutInflater.from(context);
    		notes = new ArrayList<Note>();
    		getdata();
    	}
    	
    	@SuppressWarnings("deprecation")
		public void getdata(){
    		Cursor c = note_db.get_notes();
    		startManagingCursor(c);
    	
    		if(c.moveToFirst()){
    			do{
    				String title =
    						c.getString(c.getColumnIndex(Constants.SUBJECT));
    				String content =
    						c.getString(c.getColumnIndex(Constants.CONTENT));
    				String date = c.getString(c.getColumnIndex(Constants.DATE));
    				Note temp = new Note(title);
    				temp.set_all(title, content, date);
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
}
