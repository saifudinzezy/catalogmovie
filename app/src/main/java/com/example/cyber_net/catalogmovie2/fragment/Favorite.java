package com.example.cyber_net.catalogmovie2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cyber_net.catalogmovie2.R;
import com.example.cyber_net.catalogmovie2.adapter.MovieFavAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.CONTENT_URI;

public class Favorite extends Fragment {

    @BindView(R.id.rv_now_playing)
    RecyclerView rvFavorite;
    Unbinder unbinder;
    private Context context;
    private Cursor list;
    private MovieFavAdapter adapter;

    public Favorite() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_movie, container, false);
        unbinder = ButterKnife.bind(this, view);

        //recycler
        rvFavorite.setLayoutManager(new LinearLayoutManager(context));
        rvFavorite.setHasFixedSize(true);

        adapter = new MovieFavAdapter(context);
        //set list
        adapter.setList(list);
        //setting adater
        rvFavorite.setAdapter(adapter);

        //lakukan async
        new LoadNoteAsync().execute();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //load data
    public class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        //sebelum main di bacground
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*//aktifkan progres dialog
            progressBar.setVisibility(View.VISIBLE);*/
        }

        //menjalankan di background
        //yaitu arraylist
        @Override
        protected Cursor doInBackground(Void... voids) {
            //ambil semua nilai
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        //jika hasil sudah di dapat dr background
        //dan menampilkan data
        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            /*//hilangkan pb
            progressBar.setVisibility(View.GONE);*/

            //tambahkan semua data
            list = notes;
            //set list di adapter
            adapter.setList(list);
            //notif perubahan
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0) {
                Toast.makeText(context, "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }
}
