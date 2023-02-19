package com.saptarshi.authentication.controller;

import com.saptarshi.authentication.model.AllUserResponse;
import com.saptarshi.authentication.model.Role;
import com.saptarshi.authentication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_role")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        return new ResponseEntity<>(adminService.addRole(role), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/give_role")
    public ResponseEntity<String> giveRole(@RequestParam(name = "user_email") String userEmail,
                                           @RequestParam(name = "role_name")String roleName){
        return new ResponseEntity<>(adminService.giveRole(userEmail,roleName),HttpStatus.OK);
    }

    @PostMapping("/deactivate_user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String > deactivateUser(@RequestParam(name = "user_email") String userEmail){
        return new ResponseEntity<>(adminService.deactivateUser(userEmail),HttpStatus.OK);
    }

    @PostMapping("activate_user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activateUser(@RequestParam(name = "user_email") String userEmail){
        return new ResponseEntity<>(adminService.activateUser(userEmail),HttpStatus.OK);
    }

    @DeleteMapping("/delete_user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestParam(name = "user_email")String userEmail){
        return new ResponseEntity<>(adminService.deleteUser(userEmail),HttpStatus.OK);
    }

    @GetMapping("/get_all_users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AllUserResponse> getAllUsers(){
        return new ResponseEntity<>(adminService.getAllUsers(),HttpStatus.OK);
    }

}
