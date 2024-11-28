package com.example.mobile_programming_term_project_any_info.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;

public interface JikanApiService {

    // 특정 애니메이션 정보 가져오기
    @GET("anime/{id}")
    Call<AnimeListResponse> getAnimeById(@Path("id") int animeId);

    // 현재 방영중인 애니메이션 가져오기
    @GET("top/anime?filter=airing")
    Call<AnimeListResponse> getCurrentlyAiringAnime();

    @GET("anime/{title}")
    Call<AnimeListResponse> getAnimeDetailByTitle(@Path("title") String animeTitle);

    }
