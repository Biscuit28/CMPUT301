package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

/**
 * Add item activity. Used when "new" is pressed in mainActivity
 *
 * mainActivity -> AddItemActivity
 */
public class AddItemActivity extends Activity {

    PreferenceManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        pm = new PreferenceManager(this);
    }

    /**
     * When user presses "submit", their new item will be added to file (sharedPreferences)
     * @param view
     */
    public void submitEntry(View view) {
        Intent afterSubmit = new Intent(this, MainActivity.class);
        EditText newTitle = (EditText) findViewById(R.id.new_title);
        EditText defaultVal_field = (EditText) findViewById(R.id.default_value);
        EditText description_field = (EditText) findViewById(R.id.description);
        String title = newTitle.getText().toString();
        String defaultVal = defaultVal_field.getText().toString();
        String description = description_field.getText().toString();

        //Check for empty title and value field. These fields are madnatory
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (defaultVal.isEmpty()){
            Toast.makeText(this, "Default value cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        //add user title, value, and description to file (sharedPreferences)
        else {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
            pm.addNewItem(title, timeStamp, defaultVal, description);
            Toast.makeText(this, "added to countbook!", Toast.LENGTH_SHORT).show();
            startActivity(afterSubmit);
        }
    }
}
