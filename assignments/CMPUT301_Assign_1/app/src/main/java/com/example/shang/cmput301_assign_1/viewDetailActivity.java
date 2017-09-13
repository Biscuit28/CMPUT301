package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class viewDetailActivity extends AppCompatActivity {

    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
        position = getIntent().getExtras().getString("position_detail");
        DisplayMetrics displayMetr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetr);
        int width = displayMetr.widthPixels;
        int height = displayMetr.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        displayFields(sharedPref.getString("item_title_"+position, ""),
                      sharedPref.getString("item_count_"+position, ""),
                      sharedPref.getString("item_defaultval_"+position, ""), false);
    }

    public boolean displayFields(String prev_title, String counter, String defaultVal, boolean option) {
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText title_field = (EditText) findViewById(R.id.editText_title);
        EditText count_field = (EditText) findViewById(R.id.editText_count);
        EditText default_field = (EditText) findViewById(R.id.editText_default);
        TextView detail_date = (TextView) findViewById(R.id.date_detail);
        if (option) {
            String new_title = title_field.getText().toString();
            String value = count_field.getText().toString();
            String val_default = default_field.getText().toString();
            if (new_title.isEmpty()){
                Toast.makeText(this, "title cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (value.isEmpty()){
                Toast.makeText(this, "counter cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (val_default.isEmpty()) {
                Toast.makeText(this, "default value cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                editor.putString("item_header_"+position, String.format("%s   updated on: %s   count: %s", new_title, timeStamp, val_default));
                editor.putString("item_title_"+position, new_title);
                editor.putString("item_count_"+position, value);
                editor.putString("item_defaultVal_"+position, val_default);
                editor.putString("item_timeStamp_"+position, timeStamp);
                editor.apply();
                return true;
            }
        }else {
            title_field.setText(prev_title);
            count_field.setText(counter);
            default_field.setText(defaultVal);
            detail_date.setText(sharedPref.getString("item_timeStamp_"+position, ""));
            return true;
        }
    }

    public void applyChanges(View view) {
        if (displayFields("", "", "", true)){
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            Toast.makeText(this, "changes applied!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteItem(View view) {
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int max_counter = Integer.parseInt(sharedPref.getString("max_item", "NULL"));
        for (int i = Integer.parseInt(position); i <= max_counter; i++){
            if (i != max_counter) {
                editor.putString("item_header_"+Integer.toString(i), sharedPref.getString("item_header_"+Integer.toString(i+1), "BREADPUDDING"));
                editor.putString("item_title_"+Integer.toString(i), sharedPref.getString("item_title_"+Integer.toString(i+1), "BREADPUDDING"));
                editor.putString("item_timeStamp_"+Integer.toString(i), sharedPref.getString("item_timeStamp_"+Integer.toString(i+1), "BREADPUDDING"));
                editor.putString("item_defaultval_"+Integer.toString(i), sharedPref.getString("item_defaultval_"+Integer.toString(i+1), "BREADPUDDING YEYEY"));
                editor.putString("item_count_"+Integer.toString(i), sharedPref.getString("item_count_"+Integer.toString(i+1), "BREADPUDDING"));
            }
        }
        editor.putString("max_item", Integer.toString(max_counter-1));
        editor.apply();
        Intent afterdDelete = new Intent(this, MainActivity.class);
        startActivity(afterdDelete);
        Toast.makeText(this, "item deleted!", Toast.LENGTH_SHORT).show();
    }
}
