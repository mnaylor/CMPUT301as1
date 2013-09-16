package mnaylor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbSchema extends SQLiteOpenHelper{
	
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_TABLE =
        "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
        NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        NoteEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
        NoteEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
        NoteEntry.COLUMN_NAME_SUBJECT + TEXT_TYPE + COMMA_SEP +
        NoteEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
        " )";
    
    /* Inner class that defines the table contents */
    public static abstract class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_ENTRY_ID = "noteid";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
    
    public DbSchema(Context context, String name, CursorFactory factory, 
    				int version) {
    	super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	try {
    		db.execSQL(CREATE_TABLE);
    	} catch(SQLiteException ex) {
    	}
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
    		int newVersion) {
    	db.execSQL("drop table if exists " + NoteEntry.TABLE_NAME);
    	onCreate(db);
    }    
}
