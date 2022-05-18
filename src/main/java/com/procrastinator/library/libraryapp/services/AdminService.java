package com.procrastinator.library.libraryapp.services;

import com.procrastinator.library.libraryapp.models.Admin;
import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.repositories.AdminRepository;
import com.procrastinator.library.libraryapp.repositories.UserRepository;
import com.procrastinator.library.libraryapp.requests.AdminCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.security.admin_role}")
    String ADMIN_ROLE;



    public Admin getAdmin(int adminId){
        return adminRepository.findById(adminId).orElse(null);
    }

    public void saveAdmin(AdminCreateRequest adminCreateRequest){
        Admin admin= Admin.builder().email(adminCreateRequest.getEmail())
                .name(adminCreateRequest.getName()).build();

        admin=adminRepository.save(admin);
        User user=User.builder().name(admin.getEmail())
                .password(passwordEncoder.encode(adminCreateRequest.getPassword()))
                .userTypeId(admin.getId()).createdOn( admin.getCreatedOn())
                .authorities(ADMIN_ROLE).build();

        user=userRepository.save(user);
    }

}
