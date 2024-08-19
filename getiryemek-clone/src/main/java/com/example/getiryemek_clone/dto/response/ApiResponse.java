package com.example.getiryemek_clone.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime datetime;

    public ApiResponse(boolean success,String message, T data, LocalDateTime datetime) {
        this.success=success;
        this.message = message;
        this.data = data;
        this.datetime = datetime;
    }

    public static <T> ApiResponse<T> success(String message,T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}
