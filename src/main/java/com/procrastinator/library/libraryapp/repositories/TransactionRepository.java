package com.procrastinator.library.libraryapp.repositories;

import com.procrastinator.library.libraryapp.models.Transaction;
import com.procrastinator.library.libraryapp.models.TransactionType;
import com.procrastinator.library.libraryapp.models.TrxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query("select t from Transaction t where t.student.id=:student_id and" +
            " t.book.id=:book_id and t.trxStatus=:trxStatus and t.transactionType=:transactionType" +
            " order by t.transactionTime")
    List<Transaction> getTrx(int student_id, int book_id, TrxStatus trxStatus, TransactionType transactionType);
}
