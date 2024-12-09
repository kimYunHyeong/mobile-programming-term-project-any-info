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

        @SerializedName("mal_id")  // Jikan API의 애니메이션 ID 필드
        private int id;  // 애니메이션 ID 필드 추가

        @SerializedName("images")
        private Images images;

        @SerializedName("synopsis")  // 설명 필드를 시놉시스로 추가
        private String synopsis; // 시놉시스 필드 추가

        public String getTitle() {
            return title;
        }

        public Images getImages() {
            return images;
        }

        public String getSynopsis() {
            return synopsis;
        }

        // getId() 메서드 추가
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


            @SerializedName("aired")
            private Aired aired;

            public Aired getAired() {
                return aired;
            }

            public static class Aired {
                @SerializedName("from")
                private String from;

                @SerializedName("to")
                private String to;

                public String getFrom() {
                    return from;
                }

                public String getTo() {
                    return to;
                }
            }


        @SerializedName("score")
        private String score;

        @SerializedName("popularity")
        private String popularity;

        // getScore() 메서드: score 값 반환
        public String getScore() {
            return score;
        }

        // getPopularity() 메서드: popularity 값 반환
        public String getPopularity() {
            return popularity;
        }
    }



}




