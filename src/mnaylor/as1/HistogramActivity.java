package mnaylor.as1;

import java.util.HashMap;

import mnaylor.db.Db;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

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
    	note_db = new Db(this);
    	note_db.open();
    	
        word_freq = note_db.get_word_freq();
        TextView word_cloud = (TextView) findViewById(R.id.wordCloud);
        word_cloud.setText(set_span_text(word_freq));
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
		return sText;
	}	
}
