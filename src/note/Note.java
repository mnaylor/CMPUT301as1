package note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
	public String subject;
	public String contents;
	public String note_date;
	
	/* Create a note by declaring a subject*/
	public Note(String new_subject) {
		subject = new_subject;
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ); 
		note_date = sdf.format(new Date());
	}
	
	public void set_contents(String new_contents) {
		contents = new_contents;
	}
	
	/* Use to change the date */
	// TODO
	public void set_date() {

	}
}
