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
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Spanned;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import java.util.List;





    public class DetailActivity extends AppCompatActivity {
        private TextView descriptionTextView;
        private TextView titleTextView;
        private TextView airedFromTextView;
        private TextView airedToTextView;
        private TextView genresTextView;
        private TextView rankTextView;
        private TextView ratingTextView;
        private TextView sourceTextView;
        private ImageView animeImageView;
        private JikanApiService apiService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            // UI 컴포넌트 참조
            titleTextView = findViewById(R.id.titleTextView);
            animeImageView = findViewById(R.id.animeImageView);
            descriptionTextView = findViewById(R.id.descriptionTextView);
            airedFromTextView = findViewById(R.id.airedFromTextView);
            airedToTextView = findViewById(R.id.airedToTextView);
            genresTextView = findViewById(R.id.genresTextView);
            rankTextView = findViewById(R.id.rankTextView);
            ratingTextView = findViewById(R.id.ratingTextView);
            sourceTextView = findViewById(R.id.sourceTextView);

            // 인텐트에서 데이터 받기
            Intent intent = getIntent();
            int mal_id = intent.getIntExtra("anime_id", -1);  // 애니메이션 ID로 mal_id 사용

            if (mal_id == -1) {
                Toast.makeText(this, "잘못된 애니메이션 ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Retrofit API 호출
            apiService = RetrofitClient.getClient().create(JikanApiService.class);
            Call<AnimeResponse> call = apiService.getAnimeById(mal_id);

            // 비동기 방식으로 네트워크 요청 처리
            call.enqueue(new Callback<AnimeResponse>() {
                @Override
                public void onResponse(Call<AnimeResponse> call, Response<AnimeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AnimeResponse.Data anime = response.body().getData();

                        // UI 업데이트
                        titleTextView.setText(anime.getTitle());
                        Glide.with(DetailActivity.this)
                                .load(anime.getImages().getJpg().getImageUrl())
                                .into(animeImageView);

                        // 방영 기간
                        setBoldText(airedFromTextView, "Aired From: ", anime.getAired().getFrom());
                        setBoldText(airedToTextView, "Aired To: ", anime.getAired().getTo() != null ? anime.getAired().getTo() : "On Airing");
                        setGenresTextView(genresTextView, "Genres: ", anime.getGenres());
                        setBoldText(rankTextView, "Rank: ", anime.getRank() != -1 ? String.valueOf(anime.getRank()) : "N/A");
                        setBoldText(ratingTextView, "Rating: ", anime.getRating());
                        setBoldText(sourceTextView, "Source: ", anime.getSource());
                        setBoldText(descriptionTextView, "Synopsis","\n"+anime.getSynopsis());
                    } else {
                        Toast.makeText(DetailActivity.this, "애니메이션 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AnimeResponse> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
                }
            });
        }
            private void setBoldText(TextView textView, String title, String value) {
                String fullText = title + value;
                SpannableString spannableText = new SpannableString(fullText);

                // 제목 부분 (예: "Rank: ")을 볼드체로 설정
                spannableText.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                // 텍스트를 TextView에 설정
                textView.setText(spannableText);
            }


        private void setGenresTextView(TextView genresTextView, String label, List<AnimeResponse.Data.Genre> genres) {
            // 장르 텍스트 생성
            StringBuilder genresText = new StringBuilder();

            // 장르 리스트에서 각 장르 이름을 가져와서 쉼표로 구분하여 추가
            for (int i = 0; i < genres.size(); i++) {
                genresText.append(genres.get(i).getName());
                if (i < genres.size() - 1) {
                    genresText.append(", ");  // 마지막 장르 뒤에는 쉼표 추가 안 함
                }
            }

            // 최종 생성된 텍스트를 setBoldText에 전달 (Genres:만 볼드체)
            setBoldText(genresTextView, "Genres: ", genresText.toString());  // "Genres: "는 볼드체, 그 뒤는 일반 텍스트
        }

    }