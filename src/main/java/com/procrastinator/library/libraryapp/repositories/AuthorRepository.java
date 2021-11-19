package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository  extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);
    //The name of function is findBy followed by Property-name(i.e. Email)
    //So no need write @Query Annotation

}
