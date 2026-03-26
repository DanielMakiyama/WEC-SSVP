package com.daniel.wec_ssvp.repository;

import com.daniel.wec_ssvp.model.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenciaRepository extends JpaRepository<Conferencia, Integer>{

    List<Conferencia> findByConselhoParticularId(Integer conselhoId);
}
