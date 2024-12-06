package com.example.mobile_programming_term_project_any_info;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<AnimeListResponse.Data> animeList;
    private Context context;

    public AnimeAdapter(List<AnimeListResponse.Data> animeList, Context context) {
        this.animeList = animeList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.anime_item, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        AnimeListResponse.Data anime = animeList.get(position);

        // 제목을 설정
        holder.titleTextView.setText(anime.getTitle());

        // 이미지 URL을 가져오는 경로 수정
        String imageUrl = anime.getImages().getJpg().getImageUrl();

        // Glide를 사용하여 이미지 로드
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);

        // 애니메이션 항목 클릭 시 세부 정보 페이지로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("anime_id", anime.getId());  // ID를 넘겨줌
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView imageView;

        public AnimeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.animeImageView);
        }
    }

    // 애니메이션 리스트를 업데이트하는 메서드
    public void updateAnimeList(List<AnimeListResponse.Data> newAnimeList) {
        animeList.clear();  // 기존 데이터 삭제
        animeList.addAll(newAnimeList);  // 새 데이터 추가
        notifyDataSetChanged();  // RecyclerView 갱신
    }
}
