package com.example.favorit.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favorit.R;

import butterknife.BindView;

import static com.example.favorit.db.DatabaseContract.getColumnString;
import static com.example.favorit.db.DatabaseContract.movieColumns.OVERVIEW;
import static com.example.favorit.db.DatabaseContract.movieColumns.POSTER_PATH;
import static com.example.favorit.db.DatabaseContract.movieColumns.RELEASE_DATE;
import static com.example.favorit.db.DatabaseContract.movieColumns.TITLE;

public class MovieFavAdapter extends CursorAdapter {

    public MovieFavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {

            ImageView poster = view.findViewById(R.id.poster);
            TextView txtJudul = view.findViewById(R.id.txt_judul);
            TextView txtDesc = view.findViewById(R.id.txt_desc);
            TextView txtTgl = view.findViewById(R.id.txt_tgl);

            //ambil nilai dari cursor
            txtJudul.setText(getColumnString(cursor, TITLE));
            txtDesc.setText(getColumnString(cursor, OVERVIEW));
            txtTgl.setText(getColumnString(cursor, RELEASE_DATE));
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + getColumnString(cursor, POSTER_PATH))
                    .into(poster);
        }
    }
}