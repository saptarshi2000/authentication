package com.saptarshi.authentication.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAlreadyExistException extends RuntimeException{
    private final String message;
}
