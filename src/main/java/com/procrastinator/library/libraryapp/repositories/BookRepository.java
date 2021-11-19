package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.Book;
import com.procrastinator.library.libraryapp.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Modifying
    @Transactional
    @Query("update Book b set b.cost=:cost,b.genre=:genre,b.name=:name where b.id=:id")
    void updateBook(int cost, Genre genre,String name,int id);
}
