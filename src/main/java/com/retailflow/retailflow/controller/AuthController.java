package com.retailflow.retailflow.controller;
import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.AuthResponse;
import com.retailflow.retailflow.dto.LoginRequest;
import com.retailflow.retailflow.dto.request.UserCreateReq;
import com.retailflow.retailflow.dto.response.UserCreateResp;
import com.retailflow.retailflow.jwt.JwtUtils;
import com.retailflow.retailflow.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<ResponseApi<UserCreateResp>>regiserUser(@Valid @RequestBody UserCreateReq req){
        UserCreateResp resp=userService.register(req);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("user created successfully"
                                ,resp));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // 1. Tell Spring Security to authenticate this email and password.
            // This will internally call CustomerUserDetails.loadUserByUsername() and compare the password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // 2. If the code reaches here, authentication was successful!
            // Generate the JWT token for the user.
            String token = jwtUtils.generateToken(loginRequest.getEmail());

            // 3. Return the token to the client.
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException ex) {
            // Security best practice: Don't specify whether the email or password was wrong.
            // Giving specific errors like "User not found" helps attackers guess valid emails.
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception ex) {
            ex.printStackTrace(); // Print exact error to console
            return ResponseEntity.status(500).body("An error occurred during authentication: " + ex.getClass().getName() + " - " + ex.getMessage());
        }
    }
}
