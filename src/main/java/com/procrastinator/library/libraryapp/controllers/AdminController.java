package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.Admin;
import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public Admin getAdminDetails(){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.isStudent()){
            throw  new AuthorizationServiceException("Students cannot see the admin details");
        }
        int adminId=user.getUserTypeId();
        return adminService.getAdmin(adminId);
    }

/** Admins can be added manually in the database.
 * Because Admins are limited.
 * we can also add an Admin using CommandLineRunner
 *
    @PostMapping("/admin")
    public void createAdmin(){

    }**/
}
