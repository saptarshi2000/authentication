package com.saptarshi.authentication.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {
    @NotBlank(message = "email required")
    private String email;

    @NotBlank(message = "password required")
    private String password;
}
