package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByname(String s);
}
