package com.procrastinator.library.libraryapp.requests;

import com.procrastinator.library.libraryapp.models.Student;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {
    private String name;
    private int age;
    private String country;
    private String contact;
    private String email;
    private String rollNo;

    public Student toStudent(){
        return Student.builder().name(this.name).age(this.age).country(this.country).email(this.email)
                .rollNo(this.rollNo).contact(this.contact).build();
    }

}
