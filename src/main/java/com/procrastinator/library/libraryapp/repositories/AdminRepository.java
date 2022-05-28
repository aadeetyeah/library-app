package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findByName(String name);
}
