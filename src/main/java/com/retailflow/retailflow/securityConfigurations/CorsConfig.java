package com.retailflow.retailflow.securityConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        // kon si website ko allow karna hei aur kon si ko nahi ab sab ko allow kare gae
        corsConfiguration.setAllowedOrigins(List.of("*"));
        // kon koan se methods allow karne hei
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        // kon kon se header allowed karne hei (jwt ke lye authorization header)
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        // ye rule sab par lage gae
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }
}
