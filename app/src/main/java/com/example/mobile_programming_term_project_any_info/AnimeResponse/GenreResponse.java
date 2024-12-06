package com.example.mobile_programming_term_project_any_info.AnimeResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GenreResponse {
    @SerializedName("data")
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public class Genre {
        @SerializedName("mal_id")
        private int malId;  // mal_id 추가

        @SerializedName("name")
        private String name;

        public int getMalId() {  // malId getter 추가
            return malId;
        }

        public String getName() {
            return name;
        }
    }
}

