package com.procrastinator.library.libraryapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //This Annotation is for ENUM Type only.
    //It states that how do u want to store the value as integer(Ordinals) or Enum strings.
    // as Enum starts from 0(zero)
    @Enumerated(value=EnumType.STRING)
    private Genre genre;

    private int cost;

    private boolean available;

    @CreationTimestamp
    private Date bookAddedOn;

    @JoinColumn //Add the primary key of Author table in Book table.
    @ManyToOne
    @JsonIgnoreProperties(value = "books")  //TO ignore this object when we just want to retrieve book ... to avoid recursive calls
    private Author author;      //Foreign Key

    //ManyToOne
    // First part of Annotation should belong to Current class which is book
    // Second part of Annotation should belong to the Entity on top of which we are writing the Annotation.
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("books")
    private Student student;

    @OneToMany(mappedBy = "book")       //needed to create a mapping between
    @JsonIgnoreProperties("book")       //The Annotation says that in Transaction Entity just ignore the book Field/Object.
    private List<Transaction> transactions;
}
