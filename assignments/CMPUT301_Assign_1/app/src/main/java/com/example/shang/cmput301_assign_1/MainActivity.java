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
import android.app.Activity;

/**
 * Main screen
 *
 * mainActivity
 *
 * NOTE that there is scaling issues with this app. In particular, this app runs well on
 * large screens such as nexus 6, but for screens such as nexus 5 the date in view detail activity
 * will be cutout by the multiline Textview.
 */
public class MainActivity extends Activity {

    PreferenceManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //build listview everytime MainActivity is called
        pm = new PreferenceManager(this);
        createList();
    }

    /**
     * Builds the list view from file (sharedPreferences)
     */
    public void createList(){
        //create the summart text (text right above new and reset buttons)
        TextView summary_text = (TextView) findViewById(R.id.counter_summary);
        String plr = (pm.getMax() > 1) ? "items":"item";
        summary_text.setText(String.format("You have %d %s in CountBook", pm.getMax(), plr));

        //build list
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

    /**
     * When "new" item is pressed, call add item activity
     * @param view
     */
    public void newEntry(View view) {
        Intent new_item_intent = new Intent(this, AddItemActivity.class);
        startActivity(new_item_intent);
    }

    /**
     * When "reset" is pressed, clear file (sharedPreferences)
     * @param view
     */
    public void resetAll(View view) {
        pm.resetPref();
        createList();
        Toast.makeText(this,  "countbook cleared!", Toast.LENGTH_SHORT).show();
    }
}
