package com.example.mobile_programming_term_project_any_info;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile_programming_term_project_any_info.AnimeResponse.GenreResponse;
import com.example.mobile_programming_term_project_any_info.api.JikanApiService;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;
import com.example.mobile_programming_term_project_any_info.AnimeAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JikanApiService apiService;
    private Spinner genreSpinner;
    private AnimeListFragment animeListFragment;
    private List<Integer> genreIds = new ArrayList<>();  // 장르 ID를 저장하는 리스트 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 로드
        if (savedInstanceState == null) {
            animeListFragment = new AnimeListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, animeListFragment)
                    .commit();
        }

        // Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(JikanApiService.class);

        genreSpinner = findViewById(R.id.genreSpinner);
        Button filterButton = findViewById(R.id.filterButton);

        loadGenres();
        loadCurrentlyAiringAnime(); // 앱 시작 시 상영중인 애니메이션을 불러오기

        // Filter 버튼 클릭 시 동작
        filterButton.setOnClickListener(v -> {
            int selectedGenreId = getSelectedGenreId(); // Spinner에서 선택한 장르의 ID 가져오기
            loadAnimeByGenre(selectedGenreId); // 선택된 장르에 맞는 애니메이션 불러오기
        });
    }

    private void loadGenres() {
        Call<GenreResponse> call = apiService.getAnimeGenres();

        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GenreResponse.Genre> genres = response.body().getGenres();
                    List<String> genreNames = new ArrayList<>();

                    for (GenreResponse.Genre genre : genres) {
                        genreNames.add(genre.getName());
                        genreIds.add(genre.getMalId()); // 장르 ID를 genreIds 리스트에 추가
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, genreNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    genreSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load genres", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSelectedGenreId() {
        // Spinner에서 선택된 장르의 ID를 반환
        return genreIds.get(genreSpinner.getSelectedItemPosition());
    }

    private void loadCurrentlyAiringAnime() {
        // 상영중인 애니메이션 불러오기
        Call<AnimeListResponse> call = apiService.getCurrentlyAiringAnime();
        call.enqueue(new Callback<AnimeListResponse>() {
            @Override
            public void onResponse(Call<AnimeListResponse> call, Response<AnimeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animeListFragment.updateAnimeList(response.body().getData());
                } else {
                    Toast.makeText(MainActivity.this, "현재 방영 중인 애니메이션을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeListResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAnimeByGenre(int genreId) {
        // 선택된 장르에 맞는 애니메이션 불러오기
        Call<AnimeListResponse> call = apiService.getAnimeByGenre(genreId);
        call.enqueue(new Callback<AnimeListResponse>() {
            @Override
            public void onResponse(Call<AnimeListResponse> call, Response<AnimeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animeListFragment.updateAnimeList(response.body().getData());
                } else {
                    Toast.makeText(MainActivity.this, "애니메이션 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeListResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
