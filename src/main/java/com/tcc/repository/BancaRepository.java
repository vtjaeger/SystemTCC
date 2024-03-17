package com.tcc.repository;

import com.tcc.models.Banca;
import com.tcc.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BancaRepository extends JpaRepository<Banca, Long> {
}
