package com.tcc.repository;

import com.tcc.models.Apresentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {
}
