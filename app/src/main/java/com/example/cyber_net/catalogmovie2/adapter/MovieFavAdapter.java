package com.example.cyber_net.catalogmovie2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.catalogmovie2.R;
import com.example.cyber_net.catalogmovie2.activity.DetailActivity;
import com.example.cyber_net.catalogmovie2.activity.DetailFavActivity;
import com.example.cyber_net.catalogmovie2.model.ResultsItem;

import java.util.List;

import static com.example.cyber_net.catalogmovie2.db.DatabaseContract.CONTENT_URI;

//TODO buat adapter untuk favorite
public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ViewHolder> {
    private Context context;
    private Cursor list;

    public MovieFavAdapter(Context context, Cursor list) {
        this.context = context;
        this.list = list;
    }

    public MovieFavAdapter(Context context) {
        this.context = context;
    }

    public void setList(Cursor list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ResultsItem data = getItem(position);

        holder.txtJudul.setText(data.getTitle());
        holder.txtDesc.setText(data.getOverview());
        holder.txtTgl.setText(data.getReleaseDate());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+data.getPosterPath())
                .into(holder.poster);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFavActivity.class);
                //content://com.example.cyber_net.catalogmovie2/movie/id
                Uri uri = Uri.parse(CONTENT_URI + "/" +data.getId());

                //kirim uri ke detail fav
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    //ambil nilai cursor
    private ResultsItem getItem(int position){
        if (!list.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        //ambil semua nilai cursor dan masukan di object note
        return new ResultsItem(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtDesc, txtTgl;
        ImageView poster;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);

            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtDesc = itemView.findViewById(R.id.txt_desc);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
            poster = itemView.findViewById(R.id.poster);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
