package com.progetto.ingsoftware.controller;

import com.progetto.ingsoftware.model.Utente;
import com.progetto.ingsoftware.service.UtenteService;
import com.progetto.ingsoftware.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtenteService utenteService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UtenteService utenteService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.utenteService = utenteService;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestParam String username, @RequestParam String password) {
        utenteService.registraUtente(username, password, Utente.Role.USER);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Registrazione avvenuta con successo!");
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getUserRole(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Utente> user = utenteService.trovaPerUsername(userDetails.getUsername());

        if (user.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("role", user.get().getRole().name());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
