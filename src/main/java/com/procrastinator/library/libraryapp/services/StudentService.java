package com.procrastinator.library.libraryapp.services;

import com.procrastinator.library.libraryapp.models.Student;
import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.repositories.StudentRepository;
import com.procrastinator.library.libraryapp.repositories.UserRepository;
import com.procrastinator.library.libraryapp.requests.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${app.security.student_role}")
    private String STUDENT_ROLE;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void addStudent(StudentCreateRequest studentCreateRequest){
        Student student=studentCreateRequest.toStudent();
         student=studentRepository.save(student);


         /* If we save the plain text password we will never be able to authenticate */
        User user=User.builder()
                .name(studentCreateRequest.getEmail())
                .password(passwordEncoder.encode(studentCreateRequest.getPassword()))
                .userTypeId(student.getId())
                .isStudent(true)
                .authorities(STUDENT_ROLE).build();

        userRepository.save(user);
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
