package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * Edit item activity. Used when we click on an item in the list from main activity
 *
 * mainActivity -> EditItemActivity
 */
public class EditItemActivity extends Activity {

    PreferenceManager pm;
    int position;
    int currCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        pm = new PreferenceManager(this);
        position = Integer.parseInt(getIntent().getExtras().getString("position"));
        updateScreen(4);
    }

    /**
     * Deals with updating values displayed on the screen. Option 1 updates value displayed on
     * screen with incrment, Option 2 updates value displayed on screen with decrement, Option 3
     * Deals with value when value is reset to default, any other input for Option will updatescreen
     * to what value is currently
     * @param option
     */
    public void updateScreen(int option){
        TextView count_text = (TextView) findViewById(R.id.item_count);
        TextView count_title = (TextView) findViewById(R.id.counter_title);
        if (option == 1) {
            currCount++;
        }else if (option == 2){
            if (currCount == 0) {
                //by default negative counter is not allowed. This is because you cannot have negaive
                //items in real life. Display toast when user tries to decrement a value that is 0
                Toast.makeText(this, "Negative Count not possible", Toast.LENGTH_SHORT).show();
            } else{
                currCount--;
            }
        }else if (option == 3){
            currCount = Integer.parseInt(pm.getString("item_defaultval", position, "NULL"));
        }else {
            currCount = Integer.parseInt(pm.getString("item_count", position, "NULL"));
        }
        //get the title (from file), current date, and current count
        String title = pm.getString("item_title", position, "ERROR");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        String count = Integer.toString(currCount);

        //display on screen
        count_text.setText(count);
        count_title.setText(title);

        //store current count, header, and date into file
        pm.putString("item_count", position, Integer.toString(currCount));
        pm.putString("item_timeStamp", position, date);
        pm.putString("item_header", position, String.format("%s   updated on: %s   count: %s", title, date, count));
    }

    /**
     * When +, -, "reset to default" button is pressed, call updateScreen to update the screen.
     * @param view
     */
    public void changeCounterClick(View view) {
        updateScreen(Integer.parseInt(view.getTag().toString()));
    }

    /**
     * When "Details" is pressed, Start view detail activity
     * @param view
     */
    public void viewDetails(View view) {
        Intent details = new Intent(this, viewDetailActivity.class);
        details.putExtra("position_detail", Integer.toString(position));
        startActivity(details);
    }

    /**
     * When "back" is pressed, save changes and go back to main activity.
     * @param view
     */
    public void backToMain(View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
