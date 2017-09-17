package com.example.shang.cmput301_assign_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    PreferenceManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = new PreferenceManager(this);
        createList();
    }

    public void createList(){
        TextView summary_text = (TextView) findViewById(R.id.counter_summary);
        String plr = (pm.getMax() > 1) ? "items":"item";
        summary_text.setText(String.format("You have %d %s in CountBook", pm.getMax(), plr));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pm.getHeaders());
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(arrayAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        pm.resetPref();
        createList();
        Toast.makeText(this,  "countbook cleared!", Toast.LENGTH_SHORT).show();
    }
}
