package com.example.cyber_net.catalogmovie2.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

//TODO buat sharedpref sbg session untuk fav or not fav
public class SharedFavorite {
    //buat fav yg dibutuhkan
    public static String KEY_FAVORITE = "isFave";
    private SharedPreferences preferences;

    //kode untuk membuat shared pref
    public SharedFavorite(Context context) {
        String PREFS_NAME = "Fav";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //set nilai favorite
    public void setFavorite(String id, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_FAVORITE + id, value);
        editor.apply();
    }

    public boolean getFavorite(String id) {
        //get value
        return preferences.getBoolean(KEY_FAVORITE+id, false);
    }
}
