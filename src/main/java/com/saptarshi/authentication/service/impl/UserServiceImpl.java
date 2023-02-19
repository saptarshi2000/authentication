package com.saptarshi.authentication.service.impl;

import com.saptarshi.authentication.Exception.CustomException;
import com.saptarshi.authentication.Exception.InvalidUsernameOrPasswordException;
import com.saptarshi.authentication.Exception.UserAlreadyExistException;
import com.saptarshi.authentication.model.*;
import com.saptarshi.authentication.repository.AdminRepository;
import com.saptarshi.authentication.repository.BlackListTokenRepository;
import com.saptarshi.authentication.repository.UserRepository;
import com.saptarshi.authentication.service.UserService;
import com.saptarshi.authentication.util.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final BlackListTokenRepository blackListTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTUtility jwtUtility;

    private  final UserDetailsService userDetailsService;

    @Override
    public SignUpResponse signup(User_ user) throws UserAlreadyExistException {

//        userRepository.findByEmail(user.getEmail()).ifPresent((u)->{
//            user.setCreatedOn(LocalDate.now());
//            userRepository.save(user);
//
//            new SignUpResponse("success", HttpStatus.CREATED);
//        });
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistException("user_already_exist");
        }
        Role role = adminRepository.findById((long)2).orElseThrow();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedOn(LocalDate.now());
        user.setActive(true);
        userRepository.save(user);

        return new SignUpResponse("success", HttpStatus.CREATED);

    }

    @Override
    public LogInResponse login(LogInRequest logInRequest) throws InvalidUsernameOrPasswordException {

//        User_ user = userRepository.findByEmail(logInRequest.getEmail()).orElseThrow(()->
//                new InvalidUsernameOrPasswordException("user_not_found"));

//        String password = user.getPassword();
//
//        if(!password.equals(logInRequest.getPassword())){
//            throw new InvalidUsernameOrPasswordException("incorrect_password");
//        }
        System.out.println("debug: inside login service");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getEmail(),logInRequest.getPassword()));
        }catch (AuthenticationException exception){
            throw new InvalidUsernameOrPasswordException(exception.getMessage());
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(logInRequest.getEmail());
        String accessToken = jwtUtility.generateToken(userDetails);
        String refreshToken = jwtUtility.generateRefreshToken(userDetails);
        User_ user = userRepository.findByEmail(logInRequest.getEmail()).orElseThrow();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return  new LogInResponse("success",HttpStatus.OK,accessToken,refreshToken);

    }

    @Override
    public String logout(String token,String userEmail) {
        User_ user = userRepository.findByEmail(userEmail).orElseThrow();
        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token);
        BlackListedToken blackListedRefreshToken = new BlackListedToken();
        blackListedRefreshToken.setToken(user.getRefreshToken());
        user.setRefreshToken("");
        userRepository.save(user);
        blackListTokenRepository.save(blackListedToken);
        blackListTokenRepository.save(blackListedRefreshToken);
        return "success";
    }

    @Override
    public String updatePassword(UpdatePasswordRequest updatePasswordRequest,String token){
        User_ user = userRepository.findByEmail(updatePasswordRequest.getUserEmail()).orElseThrow();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updatePasswordRequest.getUserEmail(),
                    updatePasswordRequest.getOldPassword()));
        }catch (AuthenticationException exception){
            throw new InvalidUsernameOrPasswordException(exception.getMessage());
        }
        user.setPassword(bCryptPasswordEncoder.encode(updatePasswordRequest.getNewPassword()));
        return logout(token,updatePasswordRequest.getUserEmail());
    }



    @Override
    public RefreshTokenResponse getNewAccessToken(String refreshToken,String userEmail)  {

        User_ user = userRepository.findByEmail(userEmail).orElseThrow();
        String  _refreshToken = user.getRefreshToken();
        if (!_refreshToken.equals(refreshToken)){
            throw new CustomException("authorized");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        String accessToken = jwtUtility.generateToken(userDetails);
        return new RefreshTokenResponse(accessToken);
    }
}
