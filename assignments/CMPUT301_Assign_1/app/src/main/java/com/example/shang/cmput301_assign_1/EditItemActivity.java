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
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {


    String currentPosition;
    String currCount_str;
    String countTitle;
    String countDate;
    int currCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        currentPosition = getIntent().getExtras().getString("position");
        changeCounter(4);
    }

    public void updateScreen(){
        TextView count_text = (TextView) findViewById(R.id.item_count);
        TextView count_title = (TextView) findViewById(R.id.counter_title);
        TextView count_date = (TextView) findViewById(R.id.counter_date);
        count_text.setText(currCount_str);
        count_title.setText(countTitle);
        count_date.setText(countDate);
    }

    public void changeCounter(int option){
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (option == 1) { //incremenet
            currCount++;
        }else if (option == 2){ //decrement
            if (currCount == 0) {
                Toast.makeText(this, "Negative Count not possible", Toast.LENGTH_SHORT).show();
            } else{
                currCount--;
            }
        }else if (option == 3){ //set to default
            currCount = Integer.parseInt(sharedPref.getString("item_defaultval_"+currentPosition, "NULL"));
            System.out.println("CURRENT COUNT "+ Integer.toString(currCount));
        }else { //get current
            currCount = Integer.parseInt(sharedPref.getString("item_count_"+currentPosition, "ERROR"));
            countTitle = sharedPref.getString("item_title_"+currentPosition, "ERROR");
        }
        countDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        currCount_str = Integer.toString(currCount);
        editor.putString("item_count_"+currentPosition, currCount_str);
        editor.putString("item_timeStamp_"+currentPosition, countDate);
        editor.putString("item_header_"+currentPosition, String.format("%s   updated on: %s   count: %s", countTitle, countDate, currCount_str));
        editor.apply();
        updateScreen();
    }

    public void changeCounterClick(View view) {
        changeCounter(Integer.parseInt(view.getTag().toString()));
    }

    public void viewDetails(View view) {
        Intent details = new Intent(this, viewDetailActivity.class);
        details.putExtra("position_detail", currentPosition);
        startActivity(details);
    }

    public void backToMain(View view) {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
