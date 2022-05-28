package com.procrastinator.library.libraryapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.procrastinator.library.libraryapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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


    @Bean   //This Bean is to get connected to a Redis CLient.
    public LettuceConnectionFactory getRedisFactory(){

        //While creating this object even if we don't pass hostName and port number its Okay.
        //Its default constructor default values are localhost and port=6379 only.
        //So no worries.
        //If we have password to connect with Redis we can specify that as well with setPassword() method.


        /* RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(
                "localhost",6379);      LOCAL Redis Demo */

        //REDIS CLOUD DEMO
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(
                "redis-14967.c301.ap-south-1-1.ec2.cloud.redislabs.com",14967);
        redisStandaloneConfiguration.setPassword("pXOmKZJv47sEBKg9vgvh37Q24jXRHj52");
        //redis-12763.c264.ap-south-1-1.ec2.cloud.redislabs.com:12763
        //In REDIS CLOUD after ':' the number is port i.e. 12763

        LettuceConnectionFactory lettuceConnectionFactory=
                new LettuceConnectionFactory(redisStandaloneConfiguration);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate getTemplate(){
        //If we take KEY as other than String data type for e.g. Integer.
        //It might get converted into String at Redis Level
        //Cuz Redis only supports String.

        //RedisTemplate<String,Person> redisTemplate=new RedisTemplate<>();

        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        // redisTemplate.setValueSerializer();
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(getRedisFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public ObjectMapper getMapper(){
        return new ObjectMapper();
    }
}
