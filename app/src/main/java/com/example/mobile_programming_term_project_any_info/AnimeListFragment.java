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

    //프래그먼트를 통해 애니메이션 리스트를 각각 하나의 프래그먼트로 구성하고 화면에 출력
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        animeAdapter = new AnimeAdapter(animeList, getContext());
        recyclerView.setAdapter(animeAdapter);

        apiService = RetrofitClient.getClient().create(JikanApiService.class);

        return view;
    }

    public void updateAnimeList(List<AnimeListResponse.Data> newAnimeList) {
        animeList.clear();
        animeList.addAll(newAnimeList);
        animeAdapter.notifyDataSetChanged();
    }
}
