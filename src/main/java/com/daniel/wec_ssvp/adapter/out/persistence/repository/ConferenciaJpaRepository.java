package com.daniel.wec_ssvp.adapter.out.persistence.repository;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.ConferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConferenciaJpaRepository extends JpaRepository<ConferenciaEntity, UUID> {

    List<ConferenciaEntity> findByConselhoParticularId(UUID conselhoId);

    Optional<ConferenciaEntity> findByNomeAndConselhoParticularId(String nome, UUID conselhoParticularId);
}
