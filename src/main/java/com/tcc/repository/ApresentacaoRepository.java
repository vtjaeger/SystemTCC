package com.tcc.repository;

import com.tcc.models.Apresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {
    boolean existsByDataHora(LocalDateTime dataHora);
    boolean existsByBancaId(Long bancaId);
}
