package com.saptarshi.authentication.Exception;

import com.saptarshi.authentication.model.ErrorResponse;
import com.saptarshi.authentication.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(InvalidUsernameOrPasswordException userNotFoundException){
        ErrorResponse errorResponse = new ErrorResponse(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> userAllReadyExist(UserAlreadyExistException userAlreadyExist){
        ErrorResponse errorResponse = new ErrorResponse(userAlreadyExist.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException customException){
        ErrorResponse errorResponse = new ErrorResponse(customException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> validationExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String ,String > errorResponse = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.put(fieldName,errorMessage);
        }));
        return new ResponseEntity<>(new ValidationErrorResponse(errorResponse,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }

}
