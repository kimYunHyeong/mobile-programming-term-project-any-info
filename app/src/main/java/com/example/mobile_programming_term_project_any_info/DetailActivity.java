package com.example.mobile_programming_term_project_any_info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mobile_programming_term_project_any_info.api.JikanApiService;
import com.example.mobile_programming_term_project_any_info.api.RetrofitClient;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView, descriptionTextView;
    private ImageView animeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // UI 컴포넌트 초기화
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        animeImageView = findViewById(R.id.animeImageView);

        // 애니메이션 ID나 제목을 Intent로 받기
        Intent intent = getIntent();
        String animeTitle = intent.getStringExtra("anime_id");  // 여기서는 제목을 사용, 필요 시 ID로 변경

        // 애니메이션 제목을 화면에 설정
        titleTextView.setText(animeTitle);

        // Retrofit을 사용하여 세부 애니메이션 정보 가져오기
        getAnimeDetail(animeTitle);  // 여기서 제목을 사용하여 데이터를 가져옵니다
    }

    private void getAnimeDetail(String animeTitle) {
        JikanApiService apiService = RetrofitClient.getClient().create(JikanApiService.class);
        Call<AnimeListResponse> call = apiService.getAnimeDetailByTitle(animeTitle);  // 제목으로 검색 (ID를 사용할 수도 있음)

        call.enqueue(new Callback<AnimeListResponse>() {
            @Override
            public void onResponse(Call<AnimeListResponse> call, Response<AnimeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 애니메이션 데이터 가져오기
                    AnimeListResponse.Data anime = response.body().getData().get(0);  // 첫 번째 결과 사용

                    // 이미지 URL 설정
                    String imageUrl = anime.getImages().getJpg().getImageUrl();
                    Glide.with(DetailActivity.this)
                            .load(imageUrl)
                            .into(animeImageView);

                    // 설명 설정
                    descriptionTextView.setText(anime.getSynopsis());
                } else {
                    Toast.makeText(DetailActivity.this, "애니메이션 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeListResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
