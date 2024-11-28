package com.example.mobile_programming_term_project_any_info;

import android.content.Intent;
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

        // 애니메이션 제목 설정
        holder.titleTextView.setText(anime.getTitle());

        // Glide를 사용해 대표 이미지 로드
        Glide.with(holder.itemView.getContext())
                .load(anime.getImages().getJpg().getImageUrl())  // 이미지 URL 확인 필요
//                .placeholder(R.drawable.loading)  // 로딩 중 표시할 이미지
//                .error(R.drawable.error)        // 오류 시 표시할 이미지
                .into(holder.animeImageView);         // ImageView에 이미지 설정

        // 애니메이션 항목 클릭 시 세부 정보 페이지로 이동
        holder.itemView.setOnClickListener(v -> {
            // Context는 itemView에서 얻을 수 있습니다.
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("anime_title", anime.getTitle());
            intent.putExtra("anime_synopsis", anime.getSynopsis()); // 시놉시스 전달
            intent.putExtra("anime_image_url", anime.getImages().getJpg().getImageUrl());
            holder.itemView.getContext().startActivity(intent); // DetailActivity로 이동
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    // **ViewHolder 클래스 정의**
    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView animeImageView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);   // 레이아웃의 TextView ID
            animeImageView = itemView.findViewById(R.id.animeImageView); // 레이아웃의 ImageView ID
        }
    }
}
