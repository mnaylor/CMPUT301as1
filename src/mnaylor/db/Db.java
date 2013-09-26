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

package mnaylor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

// Based on The Android Developer's Cookbook, Addison-Wesley, 2011 - Listing 9.9
public class Db {
	private static SQLiteDatabase db;
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
	
	public static Cursor get_notes() {
		try{
			Cursor c = db.query(Constants.TABLE_NAME, null, null,
								null, null, null, Constants.DATE + " DESC");
			return c;
		}
		catch(SQLiteException ex) {
			Log.v("Database query exception caught", ex.getMessage());
		}
		return null;
	}
	
	public static Cursor get_contents() {
		try {
			String[] column = {Constants.CONTENT};
			Cursor c = db.query(Constants.TABLE_NAME, column, null, null, null,
								null, null);
			return c;
		}
		catch(SQLiteException ex) {
			Log.v("Database query exception caught", ex.getMessage());
			return null;
		}
	}
	
	public long update_note(String id, String subject, String content, String date) {
		try {
			ContentValues values = new ContentValues();
			values.put(Constants.SUBJECT, subject);
			values.put(Constants.CONTENT, content);
			values.put(Constants.DATE, date);
			String whereClause = "note_id = " + id;
			
			db.update(Constants.TABLE_NAME, values, whereClause, null);
			return 0;
		}
		catch(SQLiteException ex) {
			Log.v("Update table exception caught", ex.getMessage());
			return -1;
		}
		
	}
	public long delete_note(String id) {
		try {
			String whereClause = "note_id = " + id;
			db.delete(Constants.TABLE_NAME, whereClause, null);
			return 0;
		}
		catch(SQLiteException ex) {
			Log.v("Delete row exception caught", ex.getMessage());
			return -1;
		}
	}
}
