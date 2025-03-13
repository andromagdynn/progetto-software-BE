package com.progetto.ingsoftware.dto;

import com.progetto.ingsoftware.model.Faq;

import java.time.LocalDateTime;

public class FaqDTO {
    private Long id;
    private String domanda;
    private String risposta;
    private LocalDateTime dataCreazione;

    public FaqDTO(Faq faq) {
        this.id = faq.getId();
        this.domanda = faq.getDomanda();
        this.risposta = faq.getRisposta();
        this.dataCreazione = faq.getDataCreazione();
    }

    public Long getId() {
        return id;
    }

    public String getDomanda() {
        return domanda;
    }

    public String getRisposta() {
        return risposta;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }
}
