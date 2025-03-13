package com.progetto.ingsoftware.service;

import com.progetto.ingsoftware.dto.FaqDTO;
import com.progetto.ingsoftware.model.Faq;
import com.progetto.ingsoftware.model.Utente;
import com.progetto.ingsoftware.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqDTO> getAllFaq() {
        return faqRepository.findAllByOrderByDataCreazioneDesc()
                .stream()
                .map(FaqDTO::new) // Convertiamo Faq -> FaqDTO
                .collect(Collectors.toList());
    }

    public Optional<Faq> getFaqById(Long id) {
        return faqRepository.findById(id);
    }

    public Faq addFaq(Faq faq, Utente autore) {
        faq.setAutore(autore);
        return faqRepository.save(faq);
    }

    public Faq save(Faq faq) {
        return faqRepository.save(faq);
    }

    public void deleteById(Long id) {
        faqRepository.deleteById(id);
    }
}
