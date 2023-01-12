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

                .antMatchers("/people").hasRole("ADMIN")
                .antMatchers("/people/add").hasRole("ADMIN")
                .antMatchers("/people/{id}/edit").hasRole("ADMIN")
                .antMatchers("/people/{id}/remove").hasRole("ADMIN")

                .antMatchers("/roles").hasRole("ADMIN")
                .antMatchers("/roles/add").hasRole("ADMIN")
                .antMatchers("/roles/{id}/edit").hasRole("ADMIN")
                .antMatchers("/roles/{id}/remove").hasRole("ADMIN")

                .and().formLogin().permitAll();
    }
}