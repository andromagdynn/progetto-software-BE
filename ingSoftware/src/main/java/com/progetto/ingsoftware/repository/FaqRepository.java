package com.progetto.ingsoftware.repository;

import com.progetto.ingsoftware.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findAllByOrderByDataCreazioneDesc();
}
