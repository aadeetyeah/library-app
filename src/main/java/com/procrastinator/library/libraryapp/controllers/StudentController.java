package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.Student;
import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.requests.StudentCreateRequest;
import com.procrastinator.library.libraryapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")        //similar to SIGNUP API
    public void addStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        studentService.addStudent(studentCreateRequest);
    }

    //It will be called by Student.
    @GetMapping("/student")
    public Student getStudent(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        int id=user.getId();
        if(!user.isStudent()){
            throw new AuthorizationServiceException("Admins cannot invoke this API.");
        }
        //Here we will get ID by AuthenticationContext as Student will invoke this API
        return studentService.getStudent(id);
    }

    //This API will be invoked by the HR
    @GetMapping("student/id/{id}")
    public Student getStudentById(@PathVariable("id") int id){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if(user.isStudent()){
            throw new AuthorizationServiceException("Students can't invoker this API.");
        }
        return studentService.getStudent(id);
    }
}
