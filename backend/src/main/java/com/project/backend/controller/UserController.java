package com.project.backend.controller;

import com.project.backend.entity.Role;
import com.project.backend.entity.User;
import com.project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    @PreAuthorize("hasRole('Admin')")
    public User registerNewUser(@RequestBody User user) {

        return userService.registerNewUser(user);
    }

    @PutMapping({"/editUserRoles/{userName}"})
    public User editUserRoles(@PathVariable(value="userName") String userName, @RequestBody List<Role> newRoles) {
        return userService.editUserRoles(userName, newRoles);
    }

    @GetMapping({"/getUsers"})
    @PreAuthorize("hasRole('Admin')")
    public List<User> getUsers() { return userService.getUsers(); }

    @DeleteMapping({"/deleteUser/{userName}"})
    @PreAuthorize("hasRole('Admin')")
    public void deleteUser(@PathVariable(value="userName") String userName) { userService.deleteUser(userName); }

    // Update User Details
    @PutMapping({"/updateUser/{userName}"})
    @PreAuthorize("hasAnyRole('Admin', 'UserEdit', 'UserDelete')")
    public User updateUser(@PathVariable(value="userName") String userName, @RequestBody User userDetails) { return userService.updateUser(userName, userDetails); }

}
