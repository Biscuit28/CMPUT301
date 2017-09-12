package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }


    public void addItem(String title, String timeStamp, String defaultVal) {
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String maxItems_str = sharedPref.getString("max_item", "0");
        int maxItems = Integer.parseInt(maxItems_str) + 1;
        maxItems_str = Integer.toString(maxItems);
        editor.putString("max_item", maxItems_str);
        editor.putString("item_title_"+maxItems_str, title);
        editor.putString("item_timeStamp_"+maxItems_str, timeStamp);
        editor.putString("item_defaultval_"+maxItems_str, defaultVal);
        editor.putString("item_count_"+maxItems_str, defaultVal);
        editor.apply();
    }

    public void submitEntry(View view) {
        Intent afterSubmit = new Intent(this, MainActivity.class);
        EditText newTitle = (EditText) findViewById(R.id.new_title);
        EditText defaultVal_field = (EditText) findViewById(R.id.default_value);
        String title = newTitle.getText().toString();
        String defaultVal = defaultVal_field.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (defaultVal.isEmpty()){
            Toast.makeText(this, "Default value cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
            addItem(title, timeStamp, defaultVal);
            Toast.makeText(this, "added to countbook!", Toast.LENGTH_SHORT).show();
            startActivity(afterSubmit);
        }
    }
}
