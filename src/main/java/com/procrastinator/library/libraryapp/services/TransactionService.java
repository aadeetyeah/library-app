package com.procrastinator.library.libraryapp.services;

import com.procrastinator.library.libraryapp.models.*;
import com.procrastinator.library.libraryapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Value("${books.max-allotment}")    //Autowiring a value.
    int maxAllotment;

    @Value("${books.allotted-time}")
    int allottedDays;


    @Value("${books.fine-per-day}")
    int finePerDay;

    /*
    //Whenever a book is issued or returned its TRX so we need to return TRX Id like PhonePe/PayTM
    //1.get the Book by Id + get Student by id
    //2.check for availability
    //3. check number of books that a student can own(Threshold value)
    //3. create TRX
    //5. make the book unavailable + increment student's alloted book_counter
    */
    public String issueBook(int bookId,int studentId) throws Exception{
        Book book=bookService.getBookById(bookId);
        if(book==null || !book.isAvailable()){
            return "The book is not available";
        }
        Student student=studentService.getStudent(studentId);

        if(student==null || student.getBooks().size()>=maxAllotment){
            for(Book b : student.getBooks()){
                System.out.println(b.getName());
            }
            return "Student could not be found or The max no. of books already issued.";
        }

        Transaction transaction=Transaction.builder().transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .trxStatus(TrxStatus.PENDING)
                .student(student).book(book).build();
        try {
            transaction = transactionRepository.save(transaction);

            book.setStudent(student);
            book.setAvailable(true);
            bookService.addOrUpdateBook(book);      //will update the already existing book


            transaction.setTrxStatus(TrxStatus.SUCCESS);
            transactionRepository.save(transaction);
           // throw new Exception("Transaction with id "+transaction.getTransactionId()+ " Failed");


        }catch (Exception e){

            transaction.setTrxStatus(TrxStatus.FAILED);
            book.setStudent(null);
            book.setAvailable(true);
            student.getBooks().remove(book);
            bookService.addOrUpdateBook(book);
            transactionRepository.save(transaction);
        }
        return transaction.getTransactionId();
    }



    public Integer getFine(int bookId, int studentId) {
        /* 1. Get the Issue date from TRX table for the particular student_id and book_id
        and trx should be of issue type and trx should be sort by TRX_DATE DESC and limit by 1
        and status as success.

        2.Calculate the no. of days extra - fine per day * days

         */
        List<Transaction> transactionList=transactionRepository.getTrx(studentId,bookId,TrxStatus.SUCCESS,TransactionType.ISSUE);
        Transaction transaction=transactionList.get(transactionList.size()-1);

        //Find no. of days passed from the issue date.
        Date transactionDate=transaction.getTransactionTime();
        long timeInMillis=System.currentTimeMillis()-transactionDate.getTime();
        Long daysPassed= TimeUnit.DAYS.convert(timeInMillis,TimeUnit.MILLISECONDS);

        if(daysPassed>allottedDays){
            return (daysPassed.intValue()-allottedDays)*finePerDay;
        }
        return 0;
    }

    public String returnBook(int bookId, int studentId, int fine) {
        /* 1. If fine is > 0 then increment the total fine in student_record.
        2. make book as available
        3. TRX
         */

        //Pre Condition :: Book and student should be available and book should be assigned to that student.

        Book book=bookService.getBookById(bookId);
        if(book==null || book.isAvailable()){
            return "The requested book could not be found or its already available";
        }
        Student student=studentService.getStudent(studentId);

        if(student==null || book.getStudent().getId()!=studentId){
            return "Student could not be found or book is not issued to that student.";
        }

        Transaction transaction=Transaction.builder().transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .trxStatus(TrxStatus.PENDING)
                .student(student).book(book).fine(fine).build();
        try {
            //Get the fine for this transaction and reject is actual fine is greater than what is coming in request.

            transaction = transactionRepository.save(transaction);
            book.setStudent(null);
            book.setAvailable(true);

            bookService.addOrUpdateBook(book);

            if (fine > 0) {
                student.setTotalFine(student.getTotalFine() + fine);
                studentService.addOrUpdateStudent(student);
            }
            transaction.setTrxStatus(TrxStatus.SUCCESS);
            transactionRepository.save(transaction);

        }catch (Exception e){
            transaction.setTrxStatus(TrxStatus.FAILED);
            transactionRepository.save(transaction);
        }
        return transaction.getTransactionId();
    }
}
