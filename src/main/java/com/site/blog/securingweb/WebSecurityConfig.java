package com.site.blog.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/articles").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/articles/{id}").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/articles/add").hasAnyRole("WRITER", "ADMIN")
                .antMatchers("/articles/{id}/edit").hasAnyRole("WRITER", "ADMIN")
                .antMatchers("/articles/{id}/remove").hasAnyRole("WRITER", "ADMIN")

                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/users/add").hasRole("ADMIN")
                .antMatchers("/users/{id}/edit").hasRole("ADMIN")
                .antMatchers("/users/{id}/edit/authority").hasRole("ADMIN")
                .antMatchers("/users/{id}/edit/password").hasRole("ADMIN")
                .antMatchers("/users/{id}/edit/username").hasRole("ADMIN")
                .antMatchers("/users/{id}/remove").hasRole("ADMIN")

                .antMatchers("/profile").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/profile/edit").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/profile/edit/password").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/profile/edit/username").hasAnyRole("WRITER", "ADMIN", "READER")
                .antMatchers("/profile/remove").hasAnyRole("WRITER", "ADMIN", "READER")

                .and().formLogin().permitAll();
    }
}