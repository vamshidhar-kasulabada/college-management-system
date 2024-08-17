package com.vamshidhar.cms.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter arg1,
            MediaType arg2,
            Class<? extends HttpMessageConverter<?>> arg3,
            ServerHttpRequest arg4,
            ServerHttpResponse arg5) {

        // Avoid wrapping byte arrays and other raw response types
        if (body instanceof byte[] || arg2.equals(MediaType.APPLICATION_OCTET_STREAM)) {
            return body;
        }

        // Exclude Swagger endpoints from being wrapped
        String path = arg4.getURI().getPath();
        if (path.contains("swagger") || path.contains("api-docs")) {
            return body;
        }

        if (body instanceof ApiResponse<?>) {
            return body;
        }
        return new ApiResponse<>(body);
    }

    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
