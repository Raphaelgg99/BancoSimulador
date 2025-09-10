package com.simuladorbanco.BancoDigital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs",         // JSON raiz
            "/v3/api-docs/**",      // JSON por grupo
            "/v3/api-docs.yaml",    // YAML
            "/swagger-ui.html",     // redirect helper
            "/swagger-ui/**"        // assets e index.html
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/conta/adicionar").permitAll()  // Allow POST for this route
                        .requestMatchers("/conta/listartodas").hasRole("ADMIN")
                        .requestMatchers("/conta/{numeroDaConta}/atualizar").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                ) // Authentication required for all other routes
                .csrf(AbstractHttpConfigurer::disable)// Disabling CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}master123")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(user, admin);
    } */
}