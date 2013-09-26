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
