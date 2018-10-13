package com.example.cyber_net.catalogmovie2.activity;

import android.content.ContentValues;
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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_desc_desc)
    TextView txtDescDesc;
    @BindView(R.id.txt_dect_tgl)
    TextView txtDectTgl;
    @BindView(R.id.txt_vote_average)
    TextView txtVoteAverage;
    @BindView(R.id.txt_release)
    TextView txtRelease;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.txt_vote_count)
    TextView txtVoteCount;
    @BindView(R.id.ib_fav)
    ImageButton ibFav;
    @BindView(R.id.txt_id)
    TextView txtId;

    //var
    //TODO panggil semua content value yang dibutuhkan
    private ResultsItem movie;
    //private int position;
    private MovieHelper movieHelper;
    //flag
    private Boolean isFav;
    private SharedFavorite favorite;
    //untuk menyimpan data
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //session
        favorite = new SharedFavorite(this);

        //get nilai from adapter
        final ResultsItem data = getIntent().getParcelableExtra("data");
        if (data != null) {
            getSupportActionBar().setTitle(data.getTitle());
            txtId.setText("" + data.getId());
            Log.d("id movie", "" + data.getId());
            txtDescDesc.setText(data.getOverview());
            txtDectTgl.setText(data.getReleaseDate());
            txtVoteAverage.setText("" + data.getVoteAverage());
            txtVoteCount.setText("" + data.getVoteCount());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + data.getPosterPath())
                    .into(img);
        }

        //inisialisasi nilai awal
        isFav = false;

        //cek favorite
        cekFavorite(txtId.getText().toString());

        movieHelper = new MovieHelper(this);
        //buka sqlite
        movieHelper.open();
        //buat contentvalues
        values = new ContentValues();

        //fav
        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekFavorite(txtId.getText().toString())) {
//                    ibFav.setImageResource(R.drawable.ic_star_border_black_24dp);
//                    isFav = false;
                    if (data != null) {
                        //hapus sesuai id nya
                        //content://com.example.cyber_net.catalogmovie2/movie/id
                        Uri uri = Uri.parse(CONTENT_URI + "/" + data.getId());
                        int i = getContentResolver().delete(uri, null, null);

                        //cek callback brp baris yg di hapus
                        //jika lebih dari nol maka ada data yang dihapus
                        if (i > 0) {
                            notif(v, "Hapus dari daftar Favorite");
                            //set session
                            favorite.setFavorite(KEY_FAVORITE + txtId.getText(), false);
                        }
                    }
                } else {
//                    ibFav.setImageResource(R.drawable.ic_star_black_24dp);
                    //tambah data
                    //cek jika data null
                    if (data != null) {
                        //tambahkan data
                        values.put(_ID, data.getId());   //id
                        values.put(TITLE, data.getTitle());
                        values.put(VOTE_COUNT, data.getVoteCount());
                        values.put(VOTE_AVERAGE, data.getVoteAverage()); //3
                        values.put(POSTER_PATH, data.getPosterPath());
                        values.put(OVERVIEW, data.getOverview());
                        values.put(RELEASE_DATE, data.getReleaseDate()); //3

                        //insert data
                        getContentResolver().insert(CONTENT_URI, values);
                        //notif
                        notif(v, "Disimpan ke daftar Favorite");
                        //set sesison
                        favorite.setFavorite(KEY_FAVORITE + txtId.getText(), true);
                    }
//                    isFav = true;
                }
                //cek fav
                cekFavorite(txtId.getText().toString());
            }
        });
    }

    //notifikasi
    private void notif(View v, String value) {
        Snackbar.make(v, value, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
}
