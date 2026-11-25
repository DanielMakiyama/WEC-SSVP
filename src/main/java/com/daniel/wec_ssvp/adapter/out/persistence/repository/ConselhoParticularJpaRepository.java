package com.daniel.wec_ssvp.adapter.out.persistence.repository;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.ConselhoParticularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConselhoParticularJpaRepository extends JpaRepository<ConselhoParticularEntity, UUID> {

    Optional<ConselhoParticularEntity> findByNome(String nome);
}
