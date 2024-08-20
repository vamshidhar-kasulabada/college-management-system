package com.vamshidhar.cms.advices;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

      @ExceptionHandler(MethodArgumentNotValidException.class)
  public  ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException e){
    List<String> errorMsgs = e
        .getBindingResult()
        .getAllErrors().stream()
        .map(errors->errors.getDefaultMessage())
        .toList();

      ApiError error = ApiError.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Arguments not Valid")
          .subErrors(errorMsgs)
          .build();

      return buildErrorResponseEntity(error);

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
