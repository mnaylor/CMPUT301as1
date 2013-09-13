/** Based on Android tutorial published under Creative Commons
* http://developer.android.com/training/index.html
* 
* Published under GPL-V2
* http://www.gnu.org/licenses/gpl-2.0.html
*/

package mnaylor.as1;

import mnaylor.as1.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
	
	@SuppressLint("New API")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Get the message from the intent
     Intent intent = getIntent();
     String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

     // Create the text view
     TextView textView = new TextView(this);
     textView.setTextSize(40);
     textView.setText(message);

     // Set the text view as the activity layout
     setContentView(textView);

     // Make sure we're running on Honeycomb or higher to use ActionBar APIs
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    	 // Show the Up button in the action bar.
    	 getActionBar().setDisplayHomeAsUpEnabled(true);
       }	
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
