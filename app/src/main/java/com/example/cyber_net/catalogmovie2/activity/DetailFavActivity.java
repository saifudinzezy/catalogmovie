package com.example.cyber_net.catalogmovie2.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.catalogmovie2.R;
import com.example.cyber_net.catalogmovie2.db.MovieHelper;
import com.example.cyber_net.catalogmovie2.model.ResultsItem;
import com.example.cyber_net.catalogmovie2.sharedpref.SharedFavorite;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.CONTENT_URI;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.OVERVIEW;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.POSTER_PATH;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.RELEASE_DATE;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.TITLE;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.VOTE_AVERAGE;
import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.movieColumns.VOTE_COUNT;
import static com.example.cyber_net.catalogmovie2.sharedpref.SharedFavorite.KEY_FAVORITE;

public class DetailFavActivity extends AppCompatActivity {

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

    //variabel
    private ResultsItem movie;
    //private int position;
    private MovieHelper movieHelper;
    private SharedFavorite favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movieHelper = new MovieHelper(this);
        //buka sqlite
        movieHelper.open();

        //session
        favorite = new SharedFavorite(this);

        //ambil list dari adapter
        //dengan key note
        //content://com.example.cyber_net.catalogmovie2/movie/2
        Uri uri = getIntent().getData();

        //cek jika note tdk kosong
        if (uri != null) {
            Log.d("uri", "" + uri);
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                //jika cursor tidak kosong
                //masuk ke line 1 dan buat object Note
                if (cursor.moveToFirst()) movie = new ResultsItem(cursor);
                cursor.close();
            }
        }

        //cek and set value
        if (movie != null) {
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

        //fav
        //cek favorite
        cekFavorite(txtId.getText().toString());

        //fav
        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekFavorite(txtId.getText().toString())) {

                    if (movie != null) {
                        //hapus sesuai id nya
                        //content://com.example.cyber_net.catalogmovie2/movie/id
                        Uri uri = Uri.parse(CONTENT_URI + "/" + movie.getId());
                        int i = getContentResolver().delete(uri, null, null);

                        //cek callback brp baris yg di hapus
                        //jika lebih dari nol maka ada data yang dihapus
                        if (i > 0) {
                            notif(v, "Hapus dari daftar Favorite");
                            //set session
                            favorite.setFavorite(KEY_FAVORITE + txtId.getText(), false);
                        }
                    }
                }
                //cek fav
                cekFavorite(txtId.getText().toString());
            }
        });
    }

    //cek session
    public boolean cekFavorite(String id) {
        //jika tidak ada akan bernilai false
        boolean isFav = favorite.getFavorite(KEY_FAVORITE + id);

        //jika sebelumnya favorite
        if (isFav) {
            ibFav.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            ibFav.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
        return isFav;
    }

    //notifikasi
    private void notif(View v, String value) {
        Snackbar.make(v, value, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
