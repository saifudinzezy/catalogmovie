package com.example.favorit.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favorit.R;
import com.example.favorit.entry.ResultsItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_desc_desc)
    TextView txtDescDesc;
    @BindView(R.id.txt_dect_tgl)
    TextView txtDectTgl;
    @BindView(R.id.txt_vote_average)
    TextView txtVoteAverage;
    @BindView(R.id.txt_release)
    TextView txtRelease;
    @BindView(R.id.txt_vote_count)
    TextView txtVoteCount;
    @BindView(R.id.ib_fav)
    ImageButton ibFav;
    @BindView(R.id.txt_id)
    TextView txtId;

    //var
    private ResultsItem movie = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //ambil data uri patch akhir
        Uri uri = getIntent().getData();

        if (uri != null) {
            //ambil nilai
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            // content://com.example.cyber_net.catalogmovie2/movie
            if (cursor != null) {
                if (cursor.moveToFirst()) movie = new ResultsItem(cursor);
                cursor.close();
            }

            //set nilai
            getSupportActionBar().setTitle(movie.getTitle());
            txtId.setText("" + movie.getId());
            Log.d("id movie", "" + movie.getId());
            txtDescDesc.setText(movie.getOverview());
            txtDectTgl.setText(movie.getReleaseDate());
            txtVoteAverage.setText("" + movie.getVoteAverage());
            txtVoteCount.setText("" + movie.getVoteCount());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(img);
        }
    }
}
