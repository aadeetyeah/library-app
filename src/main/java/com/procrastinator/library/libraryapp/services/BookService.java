package com.procrastinator.library.libraryapp.services;


import com.procrastinator.library.libraryapp.models.Author;
import com.procrastinator.library.libraryapp.models.Book;
import com.procrastinator.library.libraryapp.repositories.BookRepository;
import com.procrastinator.library.libraryapp.requests.BookCreateRequest;
import com.procrastinator.library.libraryapp.requests.BookUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {


    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    @Transactional
    public void addBook(BookCreateRequest bookCreateRequest){
        // Create Author object and save it in DB.
        //Create Book Object and link Author ID. (Adding the Foreign Key)  --> for this use @JoinColumn
        //Save the book Object.

    //Check whether Author already exists or not in DB.
        Author author=authorService.getAuthorByEmail(bookCreateRequest.getAuthorEmail());

        if(author==null){       //If author doesnot exist in DB.
            author=Author.builder().name(bookCreateRequest.getAuthorName())
                .age(bookCreateRequest.getAuthorAge()).country(bookCreateRequest.getAuthorCountry())
                .email(bookCreateRequest.getAuthorEmail())
                .build();
            authorService.addAuthor(author);
        }

        Book book=Book.builder()
                .cost(bookCreateRequest.getCost()).name(bookCreateRequest.getName()).available(true)
                .genre(bookCreateRequest.getGenre()).author(author)
                .build();

        bookRepository.save(book);
    }


    public Book getBookById(int bookId){
        return bookRepository.findById(bookId).orElse(null);
    }

    public void addOrUpdateBook(Book book){
        bookRepository.save(book);      //update the existing entry in DB.
    }

    public void updateBook(int bookId, BookUpdateRequest bookUpdateRequest) {
        bookRepository.updateBook(bookUpdateRequest.getCost(),bookUpdateRequest.getGenre(),bookUpdateRequest.getName(),bookId);

    }
}
