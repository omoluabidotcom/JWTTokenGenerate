package com.omoluabi.jwt.controller;

import com.omoluabi.jwt.model.JwtRequest;
import com.omoluabi.jwt.model.JwtResponse;
import com.omoluabi.jwt.service.UserService;
import com.omoluabi.jwt.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public HomeController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome Home Yahaya";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@Validated @RequestBody JwtRequest body) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername("admin");
        final String token = jwtUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }
}
