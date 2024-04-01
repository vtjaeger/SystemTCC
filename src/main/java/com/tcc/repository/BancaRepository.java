package com.tcc.repository;

import com.tcc.models.Banca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancaRepository extends JpaRepository<Banca, Long> {
    boolean existsByIntegrante1OrIntegrante2OrIntegrante3(String integrante1, String integrante2, String integrante3);
}
