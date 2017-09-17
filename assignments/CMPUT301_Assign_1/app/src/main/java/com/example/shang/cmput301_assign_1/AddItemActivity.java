package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddItemActivity extends AppCompatActivity {

    PreferenceManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        pm = new PreferenceManager(this);
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
            pm.addNewItem(title, timeStamp, defaultVal);
            Toast.makeText(this, "added to countbook!", Toast.LENGTH_SHORT).show();
            startActivity(afterSubmit);
        }
    }
}
