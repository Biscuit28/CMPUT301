package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> itemList = new ArrayList<String>();

    Intent received = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        String countBook = sharedPref.getString("data", "NULL");
        Toast.makeText(this, countBook, Toast.LENGTH_LONG).show();
        ListView myListView = (ListView) findViewById(R.id.listView);
    }

    /*method for creating a new item*/
    public void newEntry(View view) {
        Intent new_item_intent = new Intent(this, AddItemActivity.class);
        startActivity(new_item_intent);
    }
}
