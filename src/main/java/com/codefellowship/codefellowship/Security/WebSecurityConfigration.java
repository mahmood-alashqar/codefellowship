package com.codefellowship.codefellowship.Security;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;@Configuration
@EnableWebSecurity
public class WebSecurityConfigration extends WebSecurityConfigurerAdapter {@Autowired
AppSecurityService appSecurityService;    @Bean
public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(appSecurityService).passwordEncoder(passwordEncoder());
    }    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http                .authorizeRequests()
                .antMatchers("/", "/signup","/login","/*.css","/*.js","/*.png","/*.jpg","/test").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.png").permitAll()
                .antMatchers("/*.jpg").permitAll()
                .anyRequest().authenticated()//any other pages you have to be authenticated
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/myprofile")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}