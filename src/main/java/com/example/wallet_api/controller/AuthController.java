package com.example.wallet_api.controller;


import com.example.wallet_api.dto.LoginRequest;
import com.example.wallet_api.security.JwtUtil;
import com.example.wallet_api.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
    	try {
    		
  
        System.out.println("username:" + request.getUsername());
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        System.out.println("authManager:" + authManager.toString());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        System.out.println("userDetails:" + userDetails.getUsername());
        
        return jwtUtil.generateToken(userDetails.getUsername());
      	}catch (Exception ex) {
            System.out.println("Exception:" + ex.getLocalizedMessage());

      		return null;
      	}
    }
}
