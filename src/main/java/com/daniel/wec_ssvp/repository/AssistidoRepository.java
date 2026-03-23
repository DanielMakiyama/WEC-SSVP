package com.daniel.wec_ssvp.repository;

import com.daniel.wec_ssvp.model.Assistido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistidoRepository extends JpaRepository<Assistido, Integer>{
}
