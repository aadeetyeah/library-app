package com.procrastinator.library.libraryapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity //So that Hibernate can create table for Author.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author implements Serializable {       //1 Author can write multiple books (1 to MANY Relationship

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToMany(mappedBy = "author")  // (One is linked with Author and Many is linked with Book i.e. One Author to Many Books
    @JsonIgnoreProperties("author")
    private List<Book> books;

    private String country;
    private int age;

}
