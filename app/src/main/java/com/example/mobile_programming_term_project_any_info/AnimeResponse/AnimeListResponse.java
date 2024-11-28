package com.example.mobile_programming_term_project_any_info.AnimeResponse;

// AnimeResponse.java
import java.util.List;

public class AnimeListResponse {
    private List<Data> data;  // 애니메이션 리스트

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String title;  // 애니메이션 제목
        private String image_url;  // 애니메이션 대표 이미지 URL

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return image_url;
        }

        public void setImageUrl(String imageUrl) {
            this.image_url = imageUrl;
        }
    }
}





