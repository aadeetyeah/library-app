package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.Student;
import com.procrastinator.library.libraryapp.requests.StudentCreateRequest;
import com.procrastinator.library.libraryapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public void addStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        studentService.addStudent(studentCreateRequest);
    }

    @GetMapping("/student")
    public Student getStudent(@RequestParam("id") int id){
        return studentService.getStudent(id);
    }
}
