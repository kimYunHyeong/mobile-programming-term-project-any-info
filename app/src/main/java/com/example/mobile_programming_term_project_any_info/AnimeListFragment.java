package com.example.mobile_programming_term_project_any_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_programming_term_project_any_info.AnimeAdapter;
import com.example.mobile_programming_term_project_any_info.api.JikanApiService;
import com.example.mobile_programming_term_project_any_info.api.RetrofitClient;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AnimeAdapter animeAdapter;
    private List<AnimeListResponse.Data> animeList = new ArrayList<>();
    private JikanApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 애니메이션 목록 어댑터 설정
        animeAdapter = new AnimeAdapter(animeList, getContext());
        recyclerView.setAdapter(animeAdapter);

        // Retrofit API Service 초기화
        apiService = RetrofitClient.getClient().create(JikanApiService.class);

        // 데이터 로드
        getCurrentlyAiringAnime();

        return view;
    }

    private void getCurrentlyAiringAnime() {
        Call<AnimeListResponse> call = apiService.getCurrentlyAiringAnime();
        call.enqueue(new Callback<AnimeListResponse>() {
            @Override
            public void onResponse(Call<AnimeListResponse> call, Response<AnimeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animeList.clear();
                    animeList.addAll(response.body().getData());
                    animeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "현재 방영 중인 애니메이션을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 애니메이션 어댑터 클래스 (세부 정보 페이지로 이동하기 위한 클릭 리스너 설정)
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
                intent.putExtra("anime_id", anime.getTitle());  // 애니메이션 제목을 전달하거나 ID로 변경
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
    }
}
