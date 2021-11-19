package com.procrastinator.library.libraryapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String transactionId;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("transactions")   //This to avoid Recursive calls to the data/attributes
    private Student student;


    @JoinColumn     //This Annotation is used to create a Foreign Key in the mapped table
    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private Book book;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(value = EnumType.STRING)
    private TrxStatus trxStatus;

    @CreationTimestamp
    private Date transactionTime;

    private int fine;

}
