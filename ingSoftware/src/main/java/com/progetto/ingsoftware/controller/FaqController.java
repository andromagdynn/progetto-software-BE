package com.progetto.ingsoftware.controller;

import com.progetto.ingsoftware.dto.FaqDTO;
import com.progetto.ingsoftware.model.Faq;
import com.progetto.ingsoftware.model.Utente;
import com.progetto.ingsoftware.service.FaqService;
import com.progetto.ingsoftware.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FaqController {
    private final FaqService faqService;
    private final UtenteService utenteService;

    public FaqController(FaqService faqService, UtenteService utenteService) {
        this.faqService = faqService;
        this.utenteService = utenteService;
    }

    // ✅ API Pubblica per ottenere tutte le FAQ senza informazioni utente
    @GetMapping("/faq/public")
    public List<FaqDTO> getAllFaq() {
        return faqService.getAllFaq();
    }

    // ✅ API Pubblica per ottenere una FAQ specifica
    @GetMapping("/faq/{id}")
    public ResponseEntity<FaqDTO> getFaqById(@PathVariable Long id) {
        Optional<Faq> faq = faqService.getFaqById(id);
        return faq.map(f -> ResponseEntity.ok(new FaqDTO(f))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔐 Solo ADMIN possono aggiungere FAQ
    @PostMapping("/admin/add")
    public ResponseEntity<Faq> addFaq(@RequestBody Faq faq, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente autore = utenteService.trovaPerUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Faq nuovaFaq = faqService.addFaq(faq, autore);
        return ResponseEntity.ok(nuovaFaq);
    }

    // 🔐 Solo ADMIN possono modificare FAQ
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Faq> updateFaq(@PathVariable Long id, @RequestBody Faq updatedFaq) {
        Optional<Faq> faqExistente = faqService.getFaqById(id);
        if (faqExistente.isPresent()) {
            Faq faq = faqExistente.get();
            faq.setDomanda(updatedFaq.getDomanda());
            faq.setRisposta(updatedFaq.getRisposta());
            return ResponseEntity.ok(faqService.save(faq));
        }
        return ResponseEntity.notFound().build();
    }

    // 🔐 Solo ADMIN possono eliminare FAQ
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
