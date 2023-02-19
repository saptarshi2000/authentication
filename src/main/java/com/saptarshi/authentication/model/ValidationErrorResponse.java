package com.saptarshi.authentication.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final Map<String,String> message;

    private final HttpStatus status;

}
