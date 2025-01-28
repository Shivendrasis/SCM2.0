package com.scm20.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm20.services.implimentation.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    // user create and login using java code with in memory service
    // This code is only for testing
    /*
     * @Bean
     * public UserDetailsService userDetailsService(){
     * 
     * //user 1
     * UserDetails user1 = User
     * .withDefaultPasswordEncoder()
     * .username("admin123")
     * .password("admin123")
     * .roles("ADMIN", "USER")
     * .build();
     * 
     * //user 2
     * UserDetails user2 = User
     * .withUsername("user123")
     * .password("user123")
     * .build();
     * 
     * var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1 ,
     * user2);
     * return inMemoryUserDetailsManager;
     * }
     */

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenicationSuccessHandler handler;

    // configuration of authentication provider for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service's object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder's object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // url configuration
        httpSecurity.authorizeHttpRequests(authorize -> {

            // Secure all rautes related with user
            authorize.requestMatchers("/user/**").authenticated();
            // else permit all
            authorize.anyRequest().permitAll();
        });

        // form default login
        httpSecurity.formLogin(formLogin -> {

            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/login?error=true");

            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });

        // lets disable csrf token
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // logout page
        httpSecurity.logout(logoutForm -> {

            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // Oauth2 configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }

    // makes object for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
