package com.retailflow.retailflow.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// Generic API response wrapper used by all controllers to send consistent responses
// T is the type of actual data being returned (e.g., ProductCreateResp, StoreCreateResp)
@Data
@Builder
public class ResponseApi<T> {
    private  boolean success;
    private String message;
    private T data;
    private  LocalDateTime localDateTime;

    // Use when you want to return data along with a success message
    public static <T> ResponseApi<T>success(String message,T data){
        return ResponseApi.<T>builder()
                .success(true)
                .message(message)
                .localDateTime(LocalDateTime.now())
                .data(data)
                .build();
    }

    // Use when the operation succeeded but there's no data to return (e.g., delete)
    public static <T> ResponseApi<T> success(String message) {
        return ResponseApi.<T>builder()
                .success(true)
                .message(message)
                .localDateTime(LocalDateTime.now())
                .build();
    }

    // Use when something goes wrong — sets success=false and includes the error message
    public static <T> ResponseApi<T> error(String message) {
        return ResponseApi.<T>builder()
                .success(false)
                .message(message)
                .localDateTime(LocalDateTime.now())
                .build();
    }


}

