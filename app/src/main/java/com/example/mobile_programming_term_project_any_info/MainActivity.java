package com.example.mobile_programming_term_project_any_info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_programming_term_project_any_info.api.JikanApiService;
import com.example.mobile_programming_term_project_any_info.api.RetrofitClient;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimeAdapter animeAdapter;
    private List<AnimeListResponse.Data> animeList = new ArrayList<>();

    private Handler handler; // 핸들러 정의

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 애니메이션 목록 어댑터 설정
        animeAdapter = new AnimeAdapter(animeList);
        recyclerView.setAdapter(animeAdapter);

        // 핸들러 초기화: UI 업데이트를 메인 스레드에서 처리
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    // 애니메이션 목록 업데이트
                    animeList.clear();
                    animeList.addAll((List<AnimeListResponse.Data>) msg.obj);
                    animeAdapter.notifyDataSetChanged();
                } else if (msg.what == 2) {
                    // 에러 처리: 네트워크 오류 발생
                    Toast.makeText(MainActivity.this, "네트워크 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // 앱 실행 시 현재 방영 중인 애니메이션 가져오기
        getCurrentlyAiringAnime();
    }

    private void getCurrentlyAiringAnime() {
        // 별도의 스레드에서 네트워크 요청을 비동기적으로 수행
        new Thread(new Runnable() {
            @Override
            public void run() {
                JikanApiService apiService = RetrofitClient.getClient().create(JikanApiService.class);
                Call<AnimeListResponse> call = apiService.getCurrentlyAiringAnime();

                try {
                    // 네트워크 요청을 동기적으로 실행 (백그라운드 스레드에서)
                    Response<AnimeListResponse> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        // 성공적으로 데이터를 가져왔을 때
                        Message msg = handler.obtainMessage(1, response.body().getData());
                        handler.sendMessage(msg);
                    } else {
                        // 실패했을 때
                        handler.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(2); // 네트워크 오류 발생 시
                }
            }
        }).start(); // 스레드 실행
    }
}
