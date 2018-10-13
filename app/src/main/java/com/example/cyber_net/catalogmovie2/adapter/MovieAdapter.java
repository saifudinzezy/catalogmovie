package com.example.cyber_net.catalogmovie2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cyber_net.catalogmovie2.activity.DetailActivity;
import com.example.cyber_net.catalogmovie2.R;
import com.example.cyber_net.catalogmovie2.model.ResultsItem;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<ResultsItem> list;

    public MovieAdapter(Context context, List<ResultsItem> list) {
        this.context = context;
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
        holder.txtJudul.setText(list.get(position).getTitle());
        holder.txtDesc.setText(list.get(position).getOverview());
        holder.txtTgl.setText(list.get(position).getReleaseDate());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+list.get(position).getPosterPath())
                .into(holder.poster);
        final ResultsItem isi = list.get(position);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultsItem item = new ResultsItem();
                item = list.get(position);
               /* item.setTitle(isi.getTitle());
                item.setOverview(isi.getOverview());
                item.setReleaseDate(isi.getReleaseDate());
                item.setVoteCount(isi.getVoteCount());
                item.setVoteAverage(isi.getVoteAverage());
                item.setPosterPath(isi.getPosterPath());*/

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("data", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
