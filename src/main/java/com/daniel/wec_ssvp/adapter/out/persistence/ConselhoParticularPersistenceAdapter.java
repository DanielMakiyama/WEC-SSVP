package com.daniel.wec_ssvp.adapter.out.persistence;

import com.daniel.wec_ssvp.adapter.out.persistence.mapper.PersistenceMapper;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.ConselhoParticularJpaRepository;
import com.daniel.wec_ssvp.application.port.out.ConselhoParticularRepositoryPort;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Adaptador de saída: implementa a porta de Conselhos com Spring Data JPA. */
@Component
@Transactional
public class ConselhoParticularPersistenceAdapter implements ConselhoParticularRepositoryPort {

    private final ConselhoParticularJpaRepository jpaRepository;

    public ConselhoParticularPersistenceAdapter(ConselhoParticularJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ConselhoParticular salvar(ConselhoParticular conselho) {
        return PersistenceMapper.toDomain(
                jpaRepository.save(PersistenceMapper.toEntity(conselho))
        );
    }

    @Override
    public Optional<ConselhoParticular> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<ConselhoParticular> buscarPorNome(String nome) {
        return jpaRepository.findByNome(nome).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<ConselhoParticular> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }
}
