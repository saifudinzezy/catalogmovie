package com.example.cyber_net.catalogmovie2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TODO 2. buat db helper extends SQLiteOpenHelper
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;
   //max value integer Lihatlah http://www.sqlite.org/datatype3.html Maksimum 2 ^ 63-1 = 9223372036854775807
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
//                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," + //3
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)", //3
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.movieColumns._ID,
            DatabaseContract.movieColumns.TITLE,
            DatabaseContract.movieColumns.VOTE_COUNT,
            DatabaseContract.movieColumns.VOTE_AVERAGE, //3
            DatabaseContract.movieColumns.OVERVIEW,
            DatabaseContract.movieColumns.POSTER_PATH,
            DatabaseContract.movieColumns.RELEASE_DATE //3
    );

    public DatabaseHelper(Context context) {
        //contructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //buat db dengan tabel nya
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //hapus jika db version diubah
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        onCreate(db);
    }
}