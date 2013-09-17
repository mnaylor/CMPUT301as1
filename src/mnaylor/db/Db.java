/** Based on The Android Developer's Cookbook, Addison-Wesley, 2011
* Listing 9.9
* 
* Published under GPL-V2
* http://www.gnu.org/licenses/gpl-2.0.html
*/

package mnaylor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Db {
	private SQLiteDatabase db;
	private final Context context;
	private final DbHelper dbhelper;
	
	public Db(Context c){
		context = c;
		dbhelper = new DbHelper(context, Constants.DATABASE_NAME, null,
								Constants.DATABASE_VERSION);
	}

	public void close() {
		db.close();
	}
	
	public void open() throws SQLiteException {
		try {
			db = dbhelper.getWritableDatabase();
		} 
		catch(SQLiteException ex) {
			Log.v("Open database exception caught", ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}
	
	public long insert_note(String subject, String date) {
		try{
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(Constants.SUBJECT, subject);
			newTaskValue.put(Constants.DATE, date);
			return db.insert(Constants.TABLE_NAME, null, newTaskValue);
		} 
		catch(SQLiteException ex) {
			Log.v("Insert into database exception caught", ex.getMessage());
			return -1;
		}
	}
	
	public Cursor get_notes() {
		Cursor c = db.query(Constants.TABLE_NAME, null, null,
							null, null, null, null);
		return c;
	}

}