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

public class viewDetailActivity extends AppCompatActivity {

    PreferenceManager pm;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);
        pm = new PreferenceManager(this);
        position = Integer.parseInt(getIntent().getExtras().getString("position_detail"));
        DisplayMetrics displayMetr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetr);
        int width = displayMetr.widthPixels;
        int height = displayMetr.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));
        displayFields(false);
    }

    public boolean displayFields(boolean option) {
        EditText title_field = (EditText) findViewById(R.id.editText_title);
        EditText count_field = (EditText) findViewById(R.id.editText_count);
        EditText default_field = (EditText) findViewById(R.id.editText_default);
        TextView detail_date = (TextView) findViewById(R.id.date_detail);
        if (option) {
            String title_new = title_field.getText().toString();
            String value_new = count_field.getText().toString();
            String val_default_new = default_field.getText().toString();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
            if (title_new.isEmpty()){
                Toast.makeText(this, "title cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (value_new.isEmpty()){
                Toast.makeText(this, "counter cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else if (val_default_new.isEmpty()) {
                Toast.makeText(this, "default value cannot be empty", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                pm.putString("item_header", position, String.format("%s   updated on: %s   count: %s", title_new, timeStamp, value_new));
                pm.putString("item_title", position, title_new);
                pm.putString("item_count", position, value_new);
                pm.putString("item_defaultval", position, val_default_new);
                pm.putString("item_timeStamp", position, timeStamp);
                return true;
            }
        }else {
            title_field.setText(pm.getString("item_title", position, ""));
            count_field.setText(pm.getString("item_count", position, ""));
            default_field.setText(pm.getString("item_defaultval", position, ""));
            detail_date.setText(pm.getString("item_timeStamp", position, "ERROR"));
            return true;
        }
    }

    public void applyChanges(View view) {
        if (displayFields(true)){
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
            Toast.makeText(this, "changes applied!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteItem(View view) {
        pm.deleteItem(position);
        Intent afterdDelete = new Intent(this, MainActivity.class);
        startActivity(afterdDelete);
        Toast.makeText(this, "item deleted!", Toast.LENGTH_SHORT).show();
    }
}
