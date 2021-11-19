package com.procrastinator.library.libraryapp.requests;

import com.procrastinator.library.libraryapp.models.Genre;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateRequest {
    private String name;
    private Genre genre;
    private Integer cost;
}
