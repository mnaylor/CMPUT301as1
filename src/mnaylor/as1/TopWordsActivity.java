package mnaylor.as1;

import java.util.ArrayList;
import java.util.HashMap;

import mnaylor.db.Db;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TopWordsActivity extends ListActivity{
	public Db note_db;
	public HashMap<String, Integer> word_freq;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_words);
    }
	
	@Override
    protected void onStart() {
    	super.onStart();
    	ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
    	
    	note_db = new Db(this);
        note_db.open();

        word_freq = note_db.get_word_freq();
        
        // Source: 
        // http://stackoverflow.com/questions/17714580/android-listview-simple-adapter-item-repeating
        for (String key: word_freq.keySet()) {
        	HashMap<String, String> itemMap = new HashMap<String, String>();
        	itemMap.put("word", key);
        	itemMap.put("freq", word_freq.get(key).toString());
            itemList.add(itemMap);
        }
        
        SimpleAdapter adapter = new SimpleAdapter(this, itemList,
                android.R.layout.simple_list_item_2, new String[] {"word",
                        "freq"}, new int[] { android.R.id.text1,
                        android.R.id.text2 });
        setListAdapter(adapter);
    }
    
}	
