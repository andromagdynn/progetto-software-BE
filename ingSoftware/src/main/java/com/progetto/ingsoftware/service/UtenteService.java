package com.progetto.ingsoftware.service;

import com.progetto.ingsoftware.model.Utente;
import com.progetto.ingsoftware.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente registraUtente(String username, String password, Utente.Role role) {
        if (utenteRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username già in uso");
        }

        Utente utente = new Utente();
        utente.setUsername(username);
        utente.setPassword(passwordEncoder.encode(password));
        utente.setRole(role);
        return utenteRepository.save(utente);
    }

    public Optional<Utente> trovaPerUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
}
