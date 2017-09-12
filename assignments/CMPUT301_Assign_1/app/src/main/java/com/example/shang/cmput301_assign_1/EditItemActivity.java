package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {


    String currentPosition;
    String currCount_str;
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
        count_text.setText(currCount_str);
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
        }else { //get current
            currCount = Integer.parseInt(sharedPref.getString("item_count_"+currentPosition, "ERROR"));
        }
        currCount_str = Integer.toString(currCount);
        editor.putString("item_count_"+currentPosition, currCount_str);
        editor.apply();
        updateScreen();
    }

    public void changeCounterClick(View view) {
        changeCounter(Integer.parseInt(view.getTag().toString()));
    }

    //TODO - complete the delete function, add time stamp
}
