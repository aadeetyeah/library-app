package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
