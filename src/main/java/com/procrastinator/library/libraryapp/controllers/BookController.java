package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.Book;
import com.procrastinator.library.libraryapp.requests.BookCreateRequest;
import com.procrastinator.library.libraryapp.requests.BookUpdateRequest;
import com.procrastinator.library.libraryapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
        bookService.addBook(bookCreateRequest);
    }

    @GetMapping("/book")
    public Book getBook(@RequestParam("id") int BookId){
        return bookService.getBookById(BookId);
    }

    @PutMapping("/book")
    public void updateBook(@RequestParam("book_id")int bookId,
                           @RequestBody BookUpdateRequest bookUpdateRequest){
        bookService.updateBook(bookId,bookUpdateRequest);
    }

}
