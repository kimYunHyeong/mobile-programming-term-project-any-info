package com.example.mobile_programming_term_project_any_info.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeResponse;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.GenreResponse;


public interface JikanApiService {

    //애니메이션 정보 가져오기

    //상영 중인 애니메이션 가져오기
    @GET("top/anime?filter=airing")
    Call<AnimeListResponse> getCurrentlyAiringAnime();

    //애니 정보
    @GET("anime/{title}")
    Call<AnimeListResponse> getAnimeDetailByTitle(@Path("title") String animeTitle);

    //애니 아이디
    @GET("anime/{mal_id}")
    Call<AnimeResponse> getAnimeById(@Path("mal_id") int malId);

    //애니 장르
    @GET("genres/anime")
    Call<GenreResponse> getAnimeGenres();

    //장르 별로 애니메이션 불러오기
    @GET("anime")
    Call<AnimeListResponse> getAnimeByGenre(@Query("genres") int genreId);

}
