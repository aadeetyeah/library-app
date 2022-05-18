package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.Book;
import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.requests.BookCreateRequest;
import com.procrastinator.library.libraryapp.requests.BookUpdateRequest;
import com.procrastinator.library.libraryapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookService bookService;        //Field Injection

    /*
    @Autowired
    BookController(BookService bookService){
        this.bookService=bookService;
    }
    This is called as Constructor Injection.
    */

    //API should not be same - API consists of URI+Method
    //Here Methods are both different one is POST other is GET

    //URI for both methods are same.
    @PostMapping("/book")
    public void createBook(@RequestBody BookCreateRequest bookCreateRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if(user.isStudent()){
            throw new AuthorizationServiceException("Students cannot invoke this APIs");
        }
        bookService.addBook(bookCreateRequest);
    }

    @GetMapping("/book")
    public Book getBook(@RequestParam("id") int BookId){
        return bookService.getBookById(BookId);
    }

    @PutMapping("/book")
    public void updateBook(@RequestParam("book_id")int bookId,
                           @RequestBody BookUpdateRequest bookUpdateRequest){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if(user.isStudent()){
            throw new AuthorizationServiceException("Students cannot invoke this APIs");
        }
        bookService.updateBook(bookId,bookUpdateRequest);
    }

}
