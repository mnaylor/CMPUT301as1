package note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
	public String subject;
	public String contents;
	public String note_date;
	
	/* Create a note by declaring a subject*/
	public Note(String new_subject) {
		this.subject = new_subject;
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ); 
		this.note_date = sdf.format(new Date());
	}
	
	public void set_contents(String new_contents) {
		this.contents = new_contents;
	}
	
	public void set_all(String subject, String contents, String date) {
		this.subject = subject;
		this.contents = contents;
		this.note_date = date;
	}
	
	/* Use to change the date */
	// TODO
	public void set_date() {

	}
}
