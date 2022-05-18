package com.procrastinator.library.libraryapp.controllers;

import com.procrastinator.library.libraryapp.models.User;
import com.procrastinator.library.libraryapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue_book")
    public String issueBook(@RequestParam("book_id")int bookId) throws Exception {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if(!user.isStudent()){
            throw new AuthorizationServiceException("Admins cannot issue a book.");
        }
        int studentId= user.getUserTypeId();
        return transactionService.issueBook(bookId,studentId);
    }

    //student will invoke
    @GetMapping("/transaction/fine")
    public Integer getFine(@RequestParam("book_id")int bookId){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if(!user.isStudent()){
            throw new AuthorizationServiceException("Admins cannot invoke this API.");
        }
        int studentId=user.getUserTypeId();
        return transactionService.getFine(bookId,studentId);
    }

    //Admin will invoke
    @GetMapping("/transaction/fine/student_id/{id}")
    public Integer getFine(@RequestParam("book_id")int bookId,@PathVariable("id")int studentId){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if(user.isStudent()){
            throw new AuthorizationServiceException("Students cannot check someone else's Fine");
        }
        return transactionService.getFine(bookId,studentId);
    }


    @PostMapping("/transaction/return_book")
    public String returnBook(@RequestParam("book_id")int bookId,
                             @RequestParam("student_id")int studentId,
                             @RequestParam("fine")int fine){
        return transactionService.returnBook(bookId,studentId,fine);
    }

}
