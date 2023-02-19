package com.saptarshi.authentication.service;

import com.saptarshi.authentication.model.AllUserResponse;
import com.saptarshi.authentication.model.Role;

public interface AdminService {
    String addRole(Role role);

    String giveRole(String userEmail,String roleName);

    String deactivateUser(String userEmail);

    String activateUser(String userEmail);

    String deleteUser(String userEmail);

    AllUserResponse getAllUsers();

}
