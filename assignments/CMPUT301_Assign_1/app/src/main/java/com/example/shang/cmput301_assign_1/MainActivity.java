package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createList();
    }

    public void createList(){
        ArrayList<String> itemList = new ArrayList<String>();
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        String item_count_str = sharedPref.getString("max_item", "0");
        int item_count = Integer.parseInt(item_count_str);
        for (int i = 1; i <= item_count; i++){
            String title = sharedPref.getString(String.format("item_header_%d", i), "NULL");
//            System.out.println(i);
            itemList.add(title);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println(String.format("POSITION IS %s", position+1));
                Intent view_item_intent = new Intent(MainActivity.this, EditItemActivity.class);
                view_item_intent.putExtra("position", Integer.toString(position+1));
                startActivity(view_item_intent);
            }
        });
    }

    public void newEntry(View view) {
        Intent new_item_intent = new Intent(this, AddItemActivity.class);
        startActivity(new_item_intent);

    }

    public void resetAll(View view) {
        SharedPreferences sharedPref = getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        createList();
        Toast.makeText(this,  "countbook cleared!", Toast.LENGTH_SHORT).show();
    }
}
