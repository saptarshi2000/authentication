package com.saptarshi.authentication.service.impl;

import com.saptarshi.authentication.model.AllUserResponse;
import com.saptarshi.authentication.model.Role;
import com.saptarshi.authentication.model.User_;
import com.saptarshi.authentication.repository.AdminRepository;
import com.saptarshi.authentication.repository.UserRepository;
import com.saptarshi.authentication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;
    @Override
    public String addRole(Role role) {
        adminRepository.save(role);
        return "success";
    }

    @Override
    public String giveRole(String userEmail, String roleName) {

        User_ user = userRepository.findByEmail(userEmail).orElseThrow();
        Role role = adminRepository.findByRoleName(roleName).orElseThrow();

        Set<Role> roles = user.getRoles();
        if(roles.contains(role)){
            return "already role exist";
        }
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        return "success";
    }

    @Override
    public String deactivateUser(String userEmail) {

        User_ user = userRepository.findByEmail(userEmail).orElseThrow(()->
                new UsernameNotFoundException("user_don't_exist"));
        user.setActive(false);
        userRepository.save(user);
        return "success";
    }

    @Override
    public String activateUser(String userEmail) {
        User_ user = userRepository.findByEmail(userEmail).orElseThrow(()->
                new UsernameNotFoundException("user_don't_exist"));
        user.setActive(true);
        userRepository.save(user);
        return "success";
    }

    @Override
    public String deleteUser(String userEmail) {
        User_ user = userRepository.findByEmail(userEmail).orElseThrow(()->
                new UsernameNotFoundException("user_don't_exist"));
        userRepository.delete(user);
        return "success";
    }

    @Override
    public AllUserResponse getAllUsers() {
        List<User_> userS = userRepository.findAll();
        return new AllUserResponse(userS);
    }
}
