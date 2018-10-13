package com.example.cyber_net.catalogmovie2.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

//TODO 1. buat db contract dulu sbg pemetaan tabel dsb
public class DatabaseContract {
    //buat nama tabel
    public static String TABLE_MOVIE = "movie";

    //buat field2 yang dibutuhkan
    public static final class movieColumns implements BaseColumns {
        //id tidak ditulis krn nanti akan otomatis dengan bantuan basecoum _ID
        //buat field sesuai dengan result di movie db
        public static String VOTE_COUNT = "vote_count";
        //public static String VIDEO = "video";
        public static String VOTE_AVERAGE = "vote_average";
        public static String TITLE = "title"; //3
        //public static String POPULARITY = "popularity";
        public static String POSTER_PATH = "poster_path";
        //public static String ORIGINAL_LANGUAGE = "original_language";
        //public static String ORIGINAL_TITLE = "original_title";
        //public static String GENRE_IDS = "genre_ids";
        //public static String BACKDROP_PATH = "backdrop_path";
        //public static String ADULT = "adult";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date"; //3
    }

    //buat variabel untuk content uri
    /*Content provider menggunakan Uri atau (Uniform Resource Identifier) untuk mengidentifikasi proses apa yang diminta.*/
    /*Variabel AUTHORITY merupakan base authority yang akan kita gunakan untuk mengidentifikasi bahwa provider
     NoteProvider milik MyNotesApp lah yang akan diakses.*/
    //samakan dg nama package utama
    public static final String AUTHORITY = "com.example.cyber_net.catalogmovie2";

    //content://com.example.cyber_net.catalogmovie2/movie
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    //buat fungsi untuk mengambil nilainya
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
