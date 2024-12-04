package com.example.mobile_programming_term_project_any_info.AnimeResponse;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AnimeResponse {
    // 단일 애니메이션 객체를 위한 필드
    @SerializedName("data")
    private Data data;

    // 데이터 반환 메서드
    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("mal_id")
        private int malId; // mal_id 필드

        @SerializedName("images")
        private Images images;

        @SerializedName("synopsis")
        private String synopsis;

        @SerializedName("title")
        private String title;

        @SerializedName("aired")
        private Aired aired;

        @SerializedName("genres")
        private List<Genre> genres;

        @SerializedName("rank")
        private int rank;

        @SerializedName("rating")
        private String rating;

        @SerializedName("source")
        private String source;

        public String getTitle() {
            return title;
        }

        public Images getImages() {
            return images;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public Aired getAired() {
            return aired;
        }

        public List<Genre> getGenres() {
            return genres;
        }

        public int getRank() {
            return rank;
        }

        public String getRating() {
            return rating;
        }

        public String getSource() {
            return source;
        }

        // Genre 클래스 정의
        public static class Genre {
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }
        }

        // Aired 클래스 정의
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

        // Images 클래스 정의
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
}
