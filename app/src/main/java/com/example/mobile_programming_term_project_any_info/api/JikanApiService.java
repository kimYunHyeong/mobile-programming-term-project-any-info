package com.example.mobile_programming_term_project_any_info.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeResponse;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.AnimeListResponse;
import com.example.mobile_programming_term_project_any_info.AnimeResponse.GenreResponse;


public interface JikanApiService {

    // 특정 애니메이션 정보 가져오기

    // 현재 방영중인 애니메이션 가져오기
    @GET("top/anime?filter=airing")
    Call<AnimeListResponse> getCurrentlyAiringAnime();

    @GET("anime/{title}")
    Call<AnimeListResponse> getAnimeDetailByTitle(@Path("title") String animeTitle);

    @GET("anime/{mal_id}")
    Call<AnimeResponse> getAnimeById(@Path("mal_id") int malId);

    @GET("genres/anime")
    Call<GenreResponse> getAnimeGenres();

    @GET("anime")
    Call<AnimeListResponse> getAnimeByGenre(@Query("genres") int genreId);

    // 장르와 aired from 기준으로 애니메이션을 불러오는 메서드 추가
    @GET("anime")
    Call<AnimeListResponse> getAnimeByGenreAndAired(@Query("genre") int genreId, @Query("sort") String sortType);


    @GET("anime")
    Call<AnimeListResponse> getAnimeByGenreAndScore(@Query("genre") int genreId, @Query("sort") String sortType);

    @GET("anime")
    Call<AnimeListResponse> getAnimeByGenreAndPopularity(@Query("genre") int genreId, @Query("sort") String sortType);
}
