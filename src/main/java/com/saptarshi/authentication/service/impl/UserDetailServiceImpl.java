package com.saptarshi.authentication.service.impl;

import com.saptarshi.authentication.model.User_;
import com.saptarshi.authentication.repository.UserRepository;
import com.saptarshi.authentication.util.AuthUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("debug: Inside lodeUserByUserName");
        User_ user_ = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user_not found"));
    //    return new User(user_.getEmail(), user_.getPassword(), new ArrayList<>());
        return new AuthUserDetail(user_);
    }
}
