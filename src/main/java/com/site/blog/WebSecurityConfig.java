package com.site.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("adminpass")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("userpass")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/articles/add").hasRole("ADMIN")
                .antMatchers("/articles/{id}/edit").hasRole("ADMIN")
                .antMatchers("/articles/{id}/remove").hasRole("ADMIN")
                .antMatchers("/articles").hasAnyRole("USER", "ADMIN")
                .antMatchers("/articles/{id}").hasAnyRole("USER", "ADMIN")

                .antMatchers("/people/add").hasRole("ADMIN")
                .antMatchers("/people/{id}/edit").hasRole("ADMIN")
                .antMatchers("/people/{id}/remove").hasRole("ADMIN")
                .antMatchers("/people").hasAnyRole("USER", "ADMIN")

                .antMatchers("/roles/add").hasRole("ADMIN")
                .antMatchers("/roles/{id}/edit").hasRole("ADMIN")
                .antMatchers("/roles/{id}/remove").hasRole("ADMIN")
                .antMatchers("/roles").hasAnyRole("USER", "ADMIN")

                .antMatchers("/").permitAll()
                .antMatchers("/about").permitAll()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
