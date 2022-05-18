package com.procrastinator.library.libraryapp.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {

    /*Here we are defining our own User class so that we don't have to use SPring Security's default
    * User ID and Password. */


    private static  String delimeter = ":";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String password;
    private String authorities;

    private int userTypeId;

    private boolean isStudent;

    //This will be created on the basis of student or Admin's creation time
    private Date createdOn;

   /* @OneToOne
    private Student student;

    @OneToOne
    private Admin admin;
*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        this.authorities.split(delimeter);
        return Arrays.stream(this.authorities.split(delimeter))
                .map(authority->new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
