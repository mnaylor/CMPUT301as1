package mnaylor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{
	
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    private static final String CREATE_TABLE =
        "CREATE TABLE " + Constants.TABLE_NAME + " (" +
        Constants.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        Constants.SUBJECT + TEXT_TYPE + COMMA_SEP +
        Constants.DATE + TEXT_TYPE + COMMA_SEP +
        Constants.CONTENT + TEXT_TYPE + " )";
    
    public DbHelper(Context context, String name, CursorFactory factory, 
    				int version) {
    	super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	try {
    		db.execSQL(CREATE_TABLE);
    	} catch(SQLiteException ex) {
    		Log.v("Create table exception", ex.getMessage());
    	}
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
    					  int newVersion) {
    	db.execSQL("drop table if exists " + Constants.TABLE_NAME);
    	onCreate(db);
    }
}
