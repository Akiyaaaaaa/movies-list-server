package dev.akiyaaa.moviesreview.models.DTO.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private String reviewContent;
    private String imdbId;
    private String userId;
}
