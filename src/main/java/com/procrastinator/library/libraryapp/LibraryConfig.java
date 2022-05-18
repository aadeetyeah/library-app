package com.procrastinator.library.libraryapp;

import com.procrastinator.library.libraryapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Once we have included SPring Security dependency then we need to create a Config File.
 * TO Define How are we going to authenticate our application.
 *  with In-Memory Authentication, LDAP, Database Authentication,etc.
 */


/* The Config file should extend WebSecurityConfigurerAdapter class(Abstract class)
Once we extend this class then we can Override methods related to Authentication and Authorization.
* */
@Configuration
public class LibraryConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    UserService userService;

    @Value("${app.security.admin_role}")
    private String ADMIN_ROLE;

    @Value("${app.security.student_role}")
    private String STUDENT_ROLE;


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        /* This method is used to define which type of Authentication we want to do */

        authenticationManagerBuilder.userDetailsService(userService);
        //while fetching the user details the userDetailsService will use userService's loadByUserName function
        //to load the details.
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        /* This method is needed to define roles and authorities for our APIs
        * e.g. All student APIs can be invoked by student only
        * Admin related APIs can be invoked by Admin only. */

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/book/**").hasAnyAuthority(ADMIN_ROLE,STUDENT_ROLE)   //GET Method
                .antMatchers("/book/**").hasAuthority(ADMIN_ROLE)             //Any Method other than GET
                .antMatchers(HttpMethod.POST,"/student/**").permitAll()
                .antMatchers(HttpMethod.GET,"student/id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.GET,"student/**").hasAuthority(STUDENT_ROLE)
                .antMatchers(HttpMethod.GET,"/transaction/fine/student_id/**").hasAuthority(ADMIN_ROLE)
                .antMatchers("/transaction/**").hasAuthority(STUDENT_ROLE)
                .antMatchers("/admin/**").hasAuthority(ADMIN_ROLE)
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }
}
