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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// Based on The Android Developer's Cookbook, Addison-Wesley, 2011 - Listing 9.10
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
