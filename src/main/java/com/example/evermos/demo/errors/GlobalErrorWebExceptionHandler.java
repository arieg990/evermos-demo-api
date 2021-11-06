package com.example.evermos.demo.errors;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final MeterRegistry meterRegistry;

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, GlobalErrorAttributes globalErrorAttributes, MeterRegistry meterRegistry, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.meterRegistry = meterRegistry;
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(),request -> {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            if (getError(request) instanceof GlobalException) {
                status = ((GlobalException) getError(request)).getStatus();
            } else if (getError(request) instanceof ResponseStatusException) {
                status = ((ResponseStatusException) getError(request)).getStatus();
            } else if (getError(request) instanceof NoSuchElementException) {
                status = HttpStatus.NOT_FOUND;
            }

            meterRegistry.counter("http.status."+status.value()).increment();
            return ServerResponse.status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromValue(getErrorAttributes(request, ErrorAttributeOptions.defaults())));
        });
    }
}
