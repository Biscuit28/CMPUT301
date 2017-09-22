package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

/**
 * Created by shang on 9/14/2017.
 */

public class PreferenceManager {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String[] fields = {"item_header", "item_title", "item_timeStamp", "item_defaultval", "item_count"};

    public PreferenceManager(Context context){
        sharedPref = context.getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public String getString(String title, int position, String error){
        return sharedPref.getString(String.format("%s_%d", title, position), error);
    }

    public void putString(String title, int position, String text){
        editor.putString(String.format("%s_%d", title, position), text);
        editor.apply();
    }

    public int getMax(){
        return Integer.parseInt(sharedPref.getString("max_item", "0"));
    }

    public void updateMax(int increment){
        editor.putString("max_item", Integer.toString(getMax()+increment));
        editor.apply();
    }

    public ArrayList<String> getHeaders(){
        int maxCount = getMax();
        ArrayList<String> headers = new ArrayList<String>();
        for (int i = 1; i <= maxCount; i++){
            headers.add(sharedPref.getString(String.format("item_header_%d", i), "NULL"));
        }
        return headers;
    }

    public void addNewItem(String title, String date, String defaultValue){
        updateMax(1);
        putString("item_header", getMax(), String.format("%s   updated on: %s   count: %s", title, date, defaultValue));
        putString("item_title", getMax(), title);
        putString("item_timeStamp", getMax(), date);
        putString("item_defaultval", getMax(), defaultValue);
        putString("item_count", getMax(), defaultValue);
    }

    public void deleteItem(int position){
        int maxCount = getMax();
        for (int i = position; i <= maxCount; i++){
            if (i != maxCount) {
                for (String field: fields){
                    putString(field, i, getString(field, i+1, "not found"));
                }
            }
        }
        updateMax(-1);
    }

    public void resetPref(){
        editor.clear();
        editor.commit();
    }
}
