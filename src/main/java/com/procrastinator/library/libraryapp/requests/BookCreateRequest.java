package com.procrastinator.library.libraryapp.requests;

import com.procrastinator.library.libraryapp.models.Genre;
import lombok.*;

//POJO= Plain Old Java Object class
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {
    private String name;
    private Genre genre;
    private int cost;

    private String authorName;
    private String authorCountry;
    private int authorAge;

    private String authorEmail;


}
