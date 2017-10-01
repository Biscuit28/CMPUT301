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

    public void updateScreen(int option){
        TextView count_text = (TextView) findViewById(R.id.item_count);
        TextView count_title = (TextView) findViewById(R.id.counter_title);
        if (option == 1) {
            currCount++;
        }else if (option == 2){
            if (currCount == 0) {
                Toast.makeText(this, "Negative Count not possible", Toast.LENGTH_SHORT).show();
            } else{
                currCount--;
            }
        }else if (option == 3){
            currCount = Integer.parseInt(pm.getString("item_defaultval", position, "NULL"));
        }else {
            currCount = Integer.parseInt(pm.getString("item_count", position, "NULL"));
        }
        String title = pm.getString("item_title", position, "ERROR");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        String count = Integer.toString(currCount);
        count_text.setText(count);
        count_title.setText(title);
        pm.putString("item_count", position, Integer.toString(currCount));
        pm.putString("item_timeStamp", position, date);
        pm.putString("item_header", position, String.format("%s   updated on: %s   count: %s", title, date, count));
    }

    public void changeCounterClick(View view) {
        updateScreen(Integer.parseInt(view.getTag().toString()));
    }

    public void viewDetails(View view) {
        Intent details = new Intent(this, viewDetailActivity.class);
        details.putExtra("position_detail", Integer.toString(position));
        startActivity(details);
    }

    public void backToMain(View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
