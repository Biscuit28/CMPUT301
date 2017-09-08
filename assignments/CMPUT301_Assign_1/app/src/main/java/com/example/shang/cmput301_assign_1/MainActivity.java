package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> itemList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView myListView = (ListView) findViewById(R.id.listView);
    }

    /*method for creating a new item*/
    public void newEntry(View view) {
        System.out.println("whussup");
        Intent new_item_intent = new Intent(this, AddItemActivity.class);
        startActivity(new_item_intent);
    }
}
