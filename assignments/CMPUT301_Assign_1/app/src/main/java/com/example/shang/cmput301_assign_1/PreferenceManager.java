package com.example.shang.cmput301_assign_1;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

/**
 * Created by shang on 9/14/2017.
 *
 * Deals with manipulation of our saved file.
 */

public class PreferenceManager {

    //initalize sharedPreferences and its editor
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    //all the fields relevant to any given item
    private String[] fields = {"item_header", "item_title", "item_timeStamp", "item_defaultval", "item_count", "item_description"};

    /**
     * Constructor for PreferenceMangager. Creates sharedPreferences called bookCounts where our data
     * will be stored. Takes in context as sharedPreferences is inherited from
     * context.
     * @param context
     */
    public PreferenceManager(Context context){
        sharedPref = context.getSharedPreferences("book_counts", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    /**
     * Get the field of a specific item we choose. Position defines which item, title defines the field
     * of the item, error is the message we choose to return if field is not found.
     * @param title
     * @param position
     * @param error
     * @return
     */
    public String getString(String title, int position, String error){
        return sharedPref.getString(String.format("%s_%d", title, position), error);
    }

    /**
     * Insert a field of a specific item we choose. Position defines which item, title defines the field
     * of the item, text is the value of the field we put in
     * @param title
     * @param position
     * @param text
     */
    public void putString(String title, int position, String text){
        editor.putString(String.format("%s_%d", title, position), text);
        editor.apply();
    }

    /**
     * Finds the position of last item in sharedPreferences
     * @return
     */
    public int getMax(){
        return Integer.parseInt(sharedPref.getString("max_item", "0"));
    }

    /**
     * Changes position of what we define as the last item. This is neccessary when we add or delete
     * items
     * @param increment
     */
    public void updateMax(int increment){
        editor.putString("max_item", Integer.toString(getMax()+increment));
        editor.apply();
    }

    /**
     * Gets a list of Headers (headers are listview titles) and returns them as ArrayList. Note this
     * is very specific to main activity and main activity only.
     * @return
     */
    public ArrayList<String> getHeaders(){
        int maxCount = getMax();
        ArrayList<String> headers = new ArrayList<String>();
        for (int i = 1; i <= maxCount; i++){
            headers.add(sharedPref.getString(String.format("item_header_%d", i), "NULL"));
        }
        return headers;
    }

    /**
     * adds an item to sharedPrefences. Note this is very specific to add item activity.
     * @param title
     * @param date
     * @param defaultValue
     * @param description
     */
    public void addNewItem(String title, String date, String defaultValue, String description){
        updateMax(1);
        putString("item_header", getMax(), String.format("%s   updated on: %s   count: %s", title, date, defaultValue));
        putString("item_title", getMax(), title);
        putString("item_timeStamp", getMax(), date);
        putString("item_defaultval", getMax(), defaultValue);
        putString("item_count", getMax(), defaultValue);
        putString("item_description", getMax(), description);
    }

    /**
     * Deletes a item from sharedPreferences. Takes in position to define which item we are deleting
     * @param position
     */
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

    /**
     * Clears everything in sharedPreferences.
     */
    public void resetPref(){
        editor.clear();
        editor.commit();
    }
}
