package com.example.mobile_programming_term_project_any_info;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile_programming_term_project_any_info.AnimeResponse.GenreResponse;
import com.example.mobile_programming_term_project_any_info.api.JikanApiService;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 로드
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AnimeListFragment())
                    .commit();
        }

        // Retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(JikanApiService.class);

        loadGenres();
    }

    private void loadGenres() {
        Call<GenreResponse> call = apiService.getAnimeGenres();  // API 호출

        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GenreResponse.Genre> genres = response.body().getGenres();
                    List<String> genreNames = new ArrayList<>();
                    List<Integer> genreIds = new ArrayList<>();

                    for (GenreResponse.Genre genre : genres) {
                        genreNames.add(genre.getName());
                        genreIds.add(genre.getMalId()); // ID 저장
                    }

                    // Spinner에 장르 추가
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, genreNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    Spinner spinner = findViewById(R.id.genreSpinner);
                    spinner.setAdapter(adapter);

                    // 장르 선택 시 이벤트 처리
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                            int selectedGenreId = genreIds.get(position); // 선택한 장르의 ID
                            loadAnimeByGenre(selectedGenreId); // 애니메이션 불러오기
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // 아무것도 선택되지 않았을 때 처리할 내용 (필요 시)
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load genres", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 선택한 장르의 애니메이션 불러오기
    private void loadAnimeByGenre(int genreId) {
        Call<AnimeListResponse> call = apiService.getAnimeByGenre(genreId);

        call.enqueue(new Callback<AnimeListResponse>() {
            @Override
            public void onResponse(Call<AnimeListResponse> call, Response<AnimeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AnimeListFragment fragment = (AnimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    if (fragment != null) {
                        fragment.updateAnimeList(response.body().getData());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "애니메이션 데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AnimeListResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
