package com.saptarshi.authentication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String message;
    private final HttpStatus status;

}
