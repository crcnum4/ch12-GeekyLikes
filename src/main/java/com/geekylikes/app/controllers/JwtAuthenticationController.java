package com.geekylikes.app.controllers;

import com.geekylikes.app.models.bodies.JwtRequest;
import com.geekylikes.app.models.bodies.JwtResponse;
import com.geekylikes.app.service.JwtUserDetailsService;
import com.geekylikes.app.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authRequest) throws Exception {
        // Autheticate authRequest
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

//        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS", e);
        }
    }


}
