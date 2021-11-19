package com.procrastinator.library.libraryapp.services;

import com.procrastinator.library.libraryapp.models.Student;
import com.procrastinator.library.libraryapp.repositories.StudentRepository;
import com.procrastinator.library.libraryapp.requests.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void addStudent(StudentCreateRequest studentCreateRequest){
        Student student=studentCreateRequest.toStudent();
        studentRepository.save(student);
    }

    public Student getStudent(int id){
        return studentRepository.findById(id).orElse(null);
        /* If you use getById instead of findById
        * then make sure the object you are sending is Serializable
        * Primitive data types are serializable but if your object consists of User-Defined object
        * then make sure the class is serializable. */
    }

    public void addOrUpdateStudent(Student student) {
        studentRepository.save(student);
    }
}
