package com.project.backend.service;

import com.project.backend.dao.RoleDao;
import com.project.backend.dao.UserDao;
import com.project.backend.entity.Role;
import com.project.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return (List<User>) userDao.findAll();
    }

    public void deleteUser(String userName) {
         User user = userDao.findById(userName).get();
         userDao.delete(user);
    }

    public User updateUser(String userName, User userDetails) {
        User user = userDao.findById(userName).get();
        user.setEmail(userDetails.getEmail());
        user.setAddress(userDetails.getAddress());
        user.setUserPassword(getEncodedPassword(userDetails.getUserPassword()));
        return userDao.save(user);
    }

    public User registerNewUser(User user) {
        List<String> rolesName = user.getRolesName();
        Set<Role> userRoles = new HashSet<>();
        for (String roleName : rolesName){
            Role role = roleDao.findById(roleName).get();
            userRoles.add(role);
        }
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public User editUserRoles(String userName, List<Role> newRoles){
        User user = userDao.findById(userName).get();
        Set<Role> userNewRoles = new HashSet<>();
        for (Role newRole : newRoles){
            String newRoleName = newRole.getRoleName();
            Role role = roleDao.findById(newRoleName).get();
            userNewRoles.add(role);
        }
        user.setRole(userNewRoles);
        return userDao.save(user);
    }


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userEditRole = new Role();
        userEditRole.setRoleName("UserEdit");
        userEditRole.setRoleDescription("Edit Role for User");
        roleDao.save(userEditRole);

        Role userDeleteRole = new Role();
        userDeleteRole.setRoleName("UserDelete");
        userDeleteRole.setRoleDescription("Delete Role for User");
        roleDao.save(userDeleteRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setEmail("test@gmail.com");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String date = dateFormat.format(new Date());
        adminUser.setRegistrationTime();
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }



    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
