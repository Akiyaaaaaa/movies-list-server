package dev.akiyaaa.moviesreview.models.DTO.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
