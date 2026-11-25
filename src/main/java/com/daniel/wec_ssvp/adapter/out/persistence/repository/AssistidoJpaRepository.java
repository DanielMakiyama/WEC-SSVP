package com.daniel.wec_ssvp.adapter.out.persistence.repository;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.AssistidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssistidoJpaRepository extends JpaRepository<AssistidoEntity, UUID> {
}
