package com.daniel.wec_ssvp.repository;

import com.daniel.wec_ssvp.entity.Assistido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssistidoRepository extends JpaRepository<Assistido, UUID>{
}
