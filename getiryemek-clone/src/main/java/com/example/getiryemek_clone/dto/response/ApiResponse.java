package com.example.getiryemek_clone.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
   // private LocalDateTime datetime;

    public ApiResponse(boolean success,String message, T data ) {
        this.success=success;
        this.message = message;
        this.data = data;
       // this.datetime = datetime;

    }

    public static <T> ApiResponse<T> success(String message,T data) {
        return new ApiResponse<>(
                true,
                message,
                data
               // LocalDateTime.now()
        );
    }


    @JsonCreator
    public ApiResponse(@JsonProperty("message") String message,
                       @JsonProperty("data") T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(
                false,
                message,
                null
                //LocalDateTime.now()
        );
    }
}
