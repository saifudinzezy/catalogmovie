package com.example.favorit.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.favorit.R;
import com.example.favorit.adapter.MovieFavAdapter;
import com.example.favorit.db.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.example.favorit.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    @BindView(R.id.lv_notes)
    ListView lvMovie;
    //create variabel
    private MovieFavAdapter movieFavAdapter;

    //flag
    private final int LOAD_MOVIE_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Favorite");
        //create adapter
        movieFavAdapter = new MovieFavAdapter(this, null, true);
        //set adapter
        lvMovie.setAdapter(movieFavAdapter);
        //set on item click
        lvMovie.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_MOVIE_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIE_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        movieFavAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        movieFavAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_MOVIE_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        //ambil value sesuai posisi
        Cursor cursor = (Cursor) movieFavAdapter.getItem(position);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //setdata cursor
        intent.setData(Uri.parse(CONTENT_URI + "/" + id));
        startActivity(intent);
        //Toast.makeText(this, "klik", Toast.LENGTH_SHORT).show();
    }
}
