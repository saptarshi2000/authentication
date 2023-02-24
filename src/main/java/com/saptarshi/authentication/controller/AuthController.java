package com.saptarshi.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    @PostMapping("/is_user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String isUser(){
        return "success";
    }

    @PostMapping("/is_admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String isAdmin(){
        return "success";
    }

}
