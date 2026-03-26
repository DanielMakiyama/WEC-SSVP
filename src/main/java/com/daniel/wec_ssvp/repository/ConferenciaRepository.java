package com.daniel.wec_ssvp.repository;

import com.daniel.wec_ssvp.entity.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConferenciaRepository extends JpaRepository<Conferencia, UUID>{

    List<Conferencia> findByConselhoParticularId(UUID conselhoId);
}
