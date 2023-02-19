package com.saptarshi.authentication.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidUsernameOrPasswordException extends RuntimeException{
    private final String message;


//    public UserNotFoundException(String message) {
//        super(message);
//    }

}
