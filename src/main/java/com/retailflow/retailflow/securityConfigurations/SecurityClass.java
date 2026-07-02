package com.retailflow.retailflow.securityConfigurations;
import com.retailflow.retailflow.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.beans.factory.annotation.Qualifier;



import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityClass {

    private final JwtFilter jwtFilter;
    private final  CorsConfigurationSource config;

    public SecurityClass(JwtFilter jwtFilter,@Qualifier ("corsConfigurationSource") CorsConfigurationSource config) {
        this.jwtFilter = jwtFilter;
        this.config = config;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Disable CSRF (Safe because we use stateless JWT, not cookies)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(org.springframework.security.config.Customizer.withDefaults())
                // 3. Configure Endpoint Permissions
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (No token required)
                        .requestMatchers( "/auth/register","/error", "/auth/login").permitAll()
                        // All other endpoints require authentication
                        .requestMatchers("/api/owner/**").hasRole("owner").
                        requestMatchers("/api/user/**").hasRole("customer")
                        .anyRequest().authenticated()
                )
                
                // 4. Set Session Management to STATELESS
                // This means Spring will not store the user's session in memory (JSESSIONID)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 5. Add the JWT Filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
    }

    /**
     * PassworEncoder bean.
     * BCrypt is an industry standard hashing algorithm that automatically adds a salt to passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager bean.
     * We need this to manually trigger the authentication process in our AuthController during login.
     * It will automatically use our CustomerUserDetails service to fetch the user and compare the 
     * hashed password.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
