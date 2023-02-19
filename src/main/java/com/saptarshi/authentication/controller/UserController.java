package com.saptarshi.authentication.controller;

import com.saptarshi.authentication.model.*;
import com.saptarshi.authentication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody @Valid User_ user){
        System.out.println("degub: inside signup");
        SignUpResponse response = userService.signup(user);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> login(@RequestBody @Valid LogInRequest logInRequest)  {
        LogInResponse response = userService.login(logInRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/update_password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                         @RequestHeader("authorization") String token){
        return new ResponseEntity<>(userService.updatePassword(updatePasswordRequest,token.substring(7)),
                HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String > logout(@RequestHeader("authorization") String token,
                                          @RequestParam("user_email") String userEmail){
        return new ResponseEntity<>(userService.logout(token.substring(7),userEmail),HttpStatus.OK);
    }

    @GetMapping("/get_new_token")
    public ResponseEntity<RefreshTokenResponse> getNewAccessToken(@RequestHeader("authorization") String token,
                                                                  @RequestParam("user_email") String userEmail){
        return new ResponseEntity<>(userService.getNewAccessToken(token.substring(7),userEmail), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
