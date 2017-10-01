package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * Popup Window for Detail of item. Used when "DETAIL" is pressed in EditItemActivity
 *
 * mainActivity -> EditItemActivity -> viewDetailActivity
 */

public class viewDetailActivity extends Activity {

    PreferenceManager pm;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        //get the current position. Position defines which item we are refering to.
        pm = new PreferenceManager(this);
        position = Integer.parseInt(getIntent().getExtras().getString("position_detail"));

        //Set the Activity to be a popup Window which will be 80% of original height and width
        DisplayMetrics displayMetr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetr);
        int width = displayMetr.widthPixels;
        int height = displayMetr.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        //display all the values of current item in Edit text
        displayFields(false);
    }

    /**
     * Method deals all relevant data such as Title, counter, date, description and default value
     * and dislay it on edit text. If option is true, it will place all values in the EditText to file.
     * If option is false, value from file is retrieved and put into Edittext.
     *
     * Note for option 1, method will return true only if all fields that need to be filled are filled
     * We do not need to check for option 2 so it returns true by default
     *
     * @param option
     * @return
     */
    public boolean displayFields(boolean option) {
        //Initialize all view items.
        EditText title_field = (EditText) findViewById(R.id.editText_title);
        EditText count_field = (EditText) findViewById(R.id.editText_count);
        EditText default_field = (EditText) findViewById(R.id.editText_default);
        EditText description_field = (EditText) findViewById(R.id.editText_description);
        TextView detail_date = (TextView) findViewById(R.id.date_detail);

        //when we want to update the file (sharedPreferences) to whatever the user sets in the field
        if (option) {
            String title_new = title_field.getText().toString();
            String value_new = count_field.getText().toString();
            String val_default_new = default_field.getText().toString();
            String description_new = description_field.getText().toString();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

            //title, value, value default cannot be empty.
            if (title_new.isEmpty()){
                Toast.makeText(this, "title cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (value_new.isEmpty()){
                Toast.makeText(this, "counter cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (val_default_new.isEmpty()) {
                Toast.makeText(this, "default value cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else { //placing all items into file
                pm.putString("item_header", position, String.format("%s   updated on: %s   count: %s", title_new, timeStamp, value_new));
                pm.putString("item_title", position, title_new);
                pm.putString("item_count", position, value_new);
                pm.putString("item_defaultval", position, val_default_new);
                pm.putString("item_description", position, description_new);
                pm.putString("item_timeStamp", position, timeStamp);
                return true;
            }
        //when we want to display all the initial values of the item to the user in EditText. Typically
        //this option is only called when activity is first being initialized.
        }else {
            title_field.setText(pm.getString("item_title", position, ""));
            count_field.setText(pm.getString("item_count", position, ""));
            default_field.setText(pm.getString("item_defaultval", position, ""));
            description_field.setText(pm.getString("item_description", position, ""));
            detail_date.setText(pm.getString("item_timeStamp", position, "ERROR"));
            return true;
        }
    }

    /**
     * When "Apply" is pressed, values in file (sharedPreferences) are updated to whatever user
     * set in the EditText (calls displayFields)
     * @param view
     */
    public void applyChanges(View view) {
        if (displayFields(true)){
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            Toast.makeText(this, "changes applied!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When "Delete" is pressed, item is removed from file (sharedPreferences)
     * @param view
     */
    public void deleteItem(View view) {
        pm.deleteItem(position);
        Intent afterdDelete = new Intent(this, MainActivity.class);
        startActivity(afterdDelete);
        Toast.makeText(this, "item deleted!", Toast.LENGTH_SHORT).show();
    }
}
