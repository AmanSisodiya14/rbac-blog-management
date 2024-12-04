package com.rbac.Config;

import com.rbac.Filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig   {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /**
     * This method creates a security filter chain, which is a chain of filters
     * that are applied to each incoming HTTP request. The filters are
     * responsible for authenticating the user, checking the CSRF token,
     * checking the CORS configuration, and so on.
     *
     * @param http The HttpSecurity object that represents the security
     *             configuration.
     * @param jwtAuthenticationFilter The JWT authentication filter that is
     *                                 used to authenticate the user.
     * @return The SecurityFilterChain instance that represents the security
     *         filter chain.
     * @throws Exception If there is an error while creating the security filter
     *                   chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Public endpoints
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin-only endpoints
                        // .requestMatchers("/api/user/**").hasRole("USER") // User-only endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add the JWT filter

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless sessions for JWT
                );

        return http.build();
    }
    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
     *
     * This configuration allows all origins, HTTP methods, and headers.
     * It also enables credentials, such as cookies or authorization headers, to be sent.
     *
     * @return CorsConfigurationSource The source of CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all origins (adjust as needed for production)
        configuration.addAllowedOriginPattern("*");

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedMethod("*");

        // Allow all headers
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // Enable credentials (cookies, authorization headers)

        // Specific headers that are allowed
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("HttpServletRequest");

        // Register the configuration for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
