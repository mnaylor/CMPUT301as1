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

package note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Note {
	public String subject;
	public String contents;
	public String note_date;
	public int note_id;
	
	/* Create a note by declaring a subject*/
	public Note(String new_subject) {
		this.subject = new_subject;

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd, kk:mm:ss" );
		sdf.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
		this.note_date = sdf.format(new Date());
	}
	// overloaded constructor
	public Note(String subject, String contents, String date, int id) {
		this.subject = subject;
		this.contents = contents;
		this.note_date = date;
		this.note_id = id;
	}
	
	public void set_contents(String new_contents) {
		this.contents = new_contents;
	}
	
	public void set_id(int id) {
		this.note_id = id;
	}
	public String get_subject() {
		return this.subject;
	}
	public String get_contents() {
		return this.contents;
	}
	public String get_date() {
		return this.note_date;
	}	
	public Integer get_id() {
		return this.note_id;
	}
}
