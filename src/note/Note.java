package note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
	public String subject;
	public String contents;
	public String note_date;
	public int note_id;
	
	/* Create a note by declaring a subject*/
	public Note(String new_subject) {
		this.subject = new_subject;
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ); 
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
	
	public void set_all(String subject, String contents, String date, int id) {
		this.subject = subject;
		this.contents = contents;
		this.note_date = date;
		this.note_id = id;
	}
	
	/* Use to change the date */
	// TODO
	public void set_date() {

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
