package com.retailflow.retailflow.jwt;

import com.retailflow.retailflow.securityConfigurations.CustomerUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomerUserDetails customerUserDetails;



    public JwtFilter(JwtUtils jwtUtils, CustomerUserDetails customerUserDetails) {
        this.jwtUtils = jwtUtils;
        this.customerUserDetails = customerUserDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Extract the Authorization Header
        final String authHeader = request.getHeader("Authorization");
        final String email;
        final Long storeId;

        // 2. If the header is missing or doesn't start with "Bearer ", this request doesn't have a token.
        // We let the filter chain continue (maybe it's a public endpoint like /auth/login).
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // FIX: Added return to stop execution if no token is found.
        }

        try {
            // 3. Extract the token by removing the "Bearer " prefix (7 characters)
            final String jwt = authHeader.substring(7);
            
            // 4. Extract the user email from the token
            email = jwtUtils.extractEmail(jwt);
            storeId=jwtUtils.extractStoreId(jwt);

            // 5. If email is present and the user isn't already authenticated in the current context
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Fetch user details from our database via UserDetailsService
                UserDetails userDetails = customerUserDetails.loadUserByUsername(email);
                
                // Validate the token (checks expiration and matches email)
                if (jwtUtils.isTokenValid(jwt, userDetails.getUsername())) {
                    
                    // Create an Authentication token for Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            storeId,
                            userDetails.getAuthorities()
                    );
                    
                    // Add request details (like IP address, session ID) to the auth token
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Store the authentication in the context so the user is "logged in" for this request
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception ex) {
            // In a production app, you might want to log the exception or return a 401 Unauthorized here
            // instead of letting it bubble up or fail silently. For now, we print stack trace.
            System.err.println("JWT Validation Error: " + ex.getMessage());
            // Clear the context to ensure no rogue access is granted
            SecurityContextHolder.clearContext();
        }

        // 6. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
