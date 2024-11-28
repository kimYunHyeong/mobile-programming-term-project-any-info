package com.example.mobile_programming_term_project_any_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<AnimeListResponse.Data> animeList;

    public AnimeAdapter(List<AnimeListResponse.Data> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_item, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        AnimeListResponse.Data anime = animeList.get(position);
        holder.animeTitleTextView.setText(anime.getTitle());

        Glide.with(holder.animeImageView.getContext())
                .load(anime.getImageUrl())  // 이미지 URL로 로딩
                .into(holder.animeImageView);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView animeTitleTextView;
        ImageView animeImageView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            animeTitleTextView = itemView.findViewById(R.id.animeTitleTextView);
            animeImageView = itemView.findViewById(R.id.animeImageView);
        }
    }
}
