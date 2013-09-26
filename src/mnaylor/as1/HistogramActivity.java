package mnaylor.as1;

import java.util.ArrayList;
import java.util.HashMap;

import mnaylor.db.Db;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class HistogramActivity extends Activity {
	public Db note_db;
	public HashMap<String, Integer> word_freq;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
    }
	
	@Override
    protected void onStart() {
    	super.onStart();
    	ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
    	
    	note_db = new Db(this);
    	note_db.open();
    	
        word_freq = note_db.get_word_freq();
        //String word_freq_string = set_html_text(word_freq);
        TextView word_cloud = (TextView) findViewById(R.id.wordCloud);
        word_cloud.setText(set_span_text(word_freq));
        //word_cloud.setText(Html.fromHtml(word_freq_string));
	}
	
	// Source: 
	// http://stackoverflow.com/questions/16060991/why-doesnt-my-text-show-up-with-style-when-using-spannablestringbuilder
	private SpannableStringBuilder set_span_text(HashMap<String, Integer> word_freq) {
		SpannableString sWord;
		SpannableStringBuilder sText = new SpannableStringBuilder("");
		Integer freq;
		
		for (String word: word_freq.keySet()) {
			freq = word_freq.get(word) + 3;
			sWord = new SpannableString(word + " ");
			sWord.setSpan(new RelativeSizeSpan(freq), 0, sWord.length(), 0);
			sText.append(sWord);
		}
		System.out.println("Spantext = " + sText);
		return sText;
	}
	
	private String set_html_text(HashMap<String, Integer> word_freq) {
		String str_words = "";
		
		for (String word: word_freq.keySet()) {
			Integer text_size = word_freq.get(word) + 8;
			str_words += "<font size=" + text_size + ">"
						+ word + " </font>";
		}
		System.out.println(str_words);
		return str_words;
	}
	
	private String get_text_size(Integer freq) {
		switch (freq % 6) {
			case 0:
				return "h1";
			case 1:
				return "h2";
			case 2:
				return "h3";
			case 3:
				return "h4";
			case 4:
				return "h5";
			case 5:
				return "h6";
		}
		return null;
	}
}
