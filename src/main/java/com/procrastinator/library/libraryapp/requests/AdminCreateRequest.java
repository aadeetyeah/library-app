package com.procrastinator.library.libraryapp.requests;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateRequest {

    private String name;
    private String email;
    private String password;

}
