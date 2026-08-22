package com.tokio.filme.controllers;

import com.tokio.filme.dtos.AuthenticationDTO;
import com.tokio.filme.security.CustomUserDetails;
import com.tokio.filme.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class RestLoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){

        var usernamePassword = new  UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);


        String token = tokenService.generateToken((CustomUserDetails) auth.getPrincipal());

        return ResponseEntity.ok().body(token);


    }

}
