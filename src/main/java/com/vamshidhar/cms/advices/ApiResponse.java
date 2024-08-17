package com.vamshidhar.cms.advices;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private String message;
    private T data;
    private ApiError error;

    public ApiResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.message = "SUCCESS";
        this.data = data;
    }

    public ApiResponse(ApiError error){
        this();
        this.message = "FAILURE";
        this.error = error;
    }
}
