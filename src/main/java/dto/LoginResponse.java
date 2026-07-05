package com.finchat.backend.dto;

public class LoginResponse {

    private boolean success;

    private String message;

    private Long userId;

    private String username;

    private String fullName;

    private String token;



    public LoginResponse(
            boolean success,
            String message,
            Long userId,
            String username,
            String fullName,
            String token
    ) {

        this.success = success;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.token = token;

    }
    public String getToken() {

        return token;

    }

    public void setToken(String token) {

        this.token = token;

    }

    public boolean isSuccess() {

        return success;

    }

    public void setSuccess(boolean success) {

        this.success = success;

    }

    public String getMessage() {

        return message;

    }

    public void setMessage(String message) {

        this.message = message;

    }

    public Long getUserId() {

        return userId;

    }

    public void setUserId(Long userId) {

        this.userId = userId;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getFullName() {

        return fullName;

    }

    public void setFullName(String fullName) {

        this.fullName = fullName;

    }

}