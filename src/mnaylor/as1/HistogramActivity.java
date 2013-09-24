package mnaylor.as1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import mnaylor.db.Db;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
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
        SpannableString span_text = new SpannableString(set_spannable_text(word_freq));
        TextView word_cloud = (TextView) findViewById(R.id.wordCloud);
        word_cloud.setText(span_text, BufferType.SPANNABLE);
        //http://developer.android.com/reference/android/text/style/RelativeSizeSpan.html
        
	}
	
	private String set_spannable_text(HashMap<String, Integer> word_freq) {
		String str_words = "";
		Set<String> set_words;
		
		set_words = word_freq.keySet();
		for (String word: set_words) {
			str_words += word + " ";
		}
		
		return str_words;
	}
}
