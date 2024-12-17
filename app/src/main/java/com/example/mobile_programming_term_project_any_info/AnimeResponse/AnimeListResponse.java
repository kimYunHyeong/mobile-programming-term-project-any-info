package com.example.mobile_programming_term_project_any_info.AnimeResponse;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AnimeListResponse {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        private String title;

        //애니메이션 아이디
        @SerializedName("mal_id")
        private int id;

        //대표 이미지
        @SerializedName("images")
        private Images images;

        //시놉시스
        @SerializedName("synopsis")
        private String synopsis;

        public String getTitle() {
            return title;
        }

        public Images getImages() {
            return images;
        }

        public String getSynopsis() {
            return synopsis;
        }

        //아이디 받기
        public int getId() {
            return id;
        }

        public static class Images {
            @SerializedName("jpg")
            private Jpg jpg;

            public Jpg getJpg() {
                return jpg;
            }

            public static class Jpg {
                @SerializedName("image_url")
                private String imageUrl;

                public String getImageUrl() {
                    return imageUrl;
                }
            }
        }

        }


    public static class Anime {
        @SerializedName("title")
        private String title;

        @SerializedName("image_url")
        private String imageUrl;

        // 기타 필요한 필드들

        // getters
        public String getTitle() {
            return title;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

}




