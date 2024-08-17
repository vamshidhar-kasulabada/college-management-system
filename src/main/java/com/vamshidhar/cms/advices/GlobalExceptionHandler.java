package com.vamshidhar.cms.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vamshidhar.cms.exceptions.ResourceNotFoundException;



@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception e){
        return buildErrorResponseEntity(ApiErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, e));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundError(ResourceNotFoundException e){
        return buildErrorResponseEntity(ApiErrorBuilder(HttpStatus.NOT_FOUND, e));
    }




    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError err){
        return new ResponseEntity<>(new ApiResponse<>(err), err.getStatus());
    }

    private ApiError ApiErrorBuilder(HttpStatus status, Exception e){
        return ApiError.builder()
            .status(status)
            .message(e.getMessage())
            .build();
    }


}
