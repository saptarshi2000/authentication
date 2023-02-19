package com.saptarshi.authentication.service;

import com.saptarshi.authentication.model.*;

public interface UserService {
    SignUpResponse signup(User_ user);

    LogInResponse login(LogInRequest logInRequest);

    String logout(String token,String userEmail);

    String updatePassword(UpdatePasswordRequest updatePasswordRequest,String token);

    RefreshTokenResponse getNewAccessToken(String refreshToken,String userEmail);

}

