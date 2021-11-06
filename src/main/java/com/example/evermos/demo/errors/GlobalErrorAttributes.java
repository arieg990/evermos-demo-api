package com.example.evermos.demo.errors;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        if (error instanceof GlobalException) {
            GlobalException ex = (GlobalException) getError(request);
            errorAttributes.put("exception", ex.getClass().getSimpleName());
            errorAttributes.put("message", ex.getMessage());
            errorAttributes.put("status", ex.getStatus().value());
            errorAttributes.put("error", ex.getStatus().getReasonPhrase());
        } else if (error instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) getError(request);
            errorAttributes.put("exception", ex.getClass().getSimpleName());
            errorAttributes.put("message", ex.getMessage());
            errorAttributes.put("status", ex.getStatus().value());
            errorAttributes.put("error", ex.getStatus().getReasonPhrase());
        } else if (error instanceof NoSuchElementException) {
            NoSuchElementException ex = (NoSuchElementException) getError(request);
            errorAttributes.put("exception", ex.getClass().getSimpleName());
            errorAttributes.put("message", ex.getMessage());
            errorAttributes.put("status", HttpStatus.NOT_FOUND.value());
            errorAttributes.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        } else {
            errorAttributes.put("exception", error.getClass().getSimpleName());
            errorAttributes.put("message", error.getMessage() == null ? "Something went wrong" : error.getMessage());
            errorAttributes.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorAttributes.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        return errorAttributes;
    }
}
