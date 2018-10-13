package com.example.cyber_net.catalogmovie2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.cyber_net.catalogmovie2.db.DatabaseContract;
import com.example.cyber_net.catalogmovie2.db.MovieHelper;

import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.AUTHORITY;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.CONTENT_URI;

//TODO 5 buat class provider extends ContentProvider untuk fungsi CRUD
public class MovieProvider extends ContentProvider {
    //buat variabel sbg penanda/flag untuk mempermudah logika nantinya
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

     /* URI ini berisi konten: //com.example.todo/todo/ * */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.cyber_net.catalogmovie2/movie
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);
        /*content://com.example.cyber_net.catalogmovie2/note/id, di dalam uri matcher menggunakan
        symbol # untuk matchernya. Ini menandakan bahwa tanda # nanti akan diganti dengan id tertentu,
        sama seperti fungsi ? di dalam query atau %s di dalam string.*/
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE + "/#", MOVIE_ID);
    }

    //
    private MovieHelper movieHelper;

    //buat obejct saat pertama kali
    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                delete = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                delete = 0;
                break;
        }
        if (delete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delete;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int update;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                update = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                update = 0;
                break;
        }
        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return update;
    }
}