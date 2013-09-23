package note;

public class Stats {
	public String contents;
	public int char_count;
	public int word_count;
	
	public Stats(String contents) {
		this.contents = contents;
		this.char_count = get_char_count();
		this.word_count = get_word_count();
	}
	
	private int get_char_count() {
		return this.contents.length();
	}
	
	private int get_word_count() {
		if (this.contents.length() == 0) {
			return 0;
		}
		String[] temp = this.contents.split(" ");
		return temp.length;
	}
}
