package com.daniel.wec_ssvp.adapter.out.persistence;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.ConferenciaEntity;
import com.daniel.wec_ssvp.adapter.out.persistence.mapper.PersistenceMapper;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.ConferenciaJpaRepository;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.ConselhoParticularJpaRepository;
import com.daniel.wec_ssvp.application.port.out.ConferenciaRepositoryPort;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Adaptador de saída: implementa a porta de Conferências com Spring Data JPA. */
@Component
@Transactional
public class ConferenciaPersistenceAdapter implements ConferenciaRepositoryPort {

    private final ConferenciaJpaRepository jpaRepository;
    private final ConselhoParticularJpaRepository conselhoJpaRepository;

    public ConferenciaPersistenceAdapter(ConferenciaJpaRepository jpaRepository,
                                         ConselhoParticularJpaRepository conselhoJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.conselhoJpaRepository = conselhoJpaRepository;
    }

    @Override
    public Conferencia salvar(Conferencia conferencia) {
        ConferenciaEntity entity = PersistenceMapper.toEntity(conferencia);
        entity.setConselhoParticular(
                conselhoJpaRepository.getReferenceById(conferencia.getConselhoParticular().getId())
        );
        return PersistenceMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Conferencia> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<Conferencia> buscarPorNomeEConselho(String nome, UUID conselhoParticularId) {
        return jpaRepository.findByNomeAndConselhoParticularId(nome, conselhoParticularId)
                .map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Conferencia> listarTodas() {
        return jpaRepository.findAll().stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Conferencia> listarPorConselho(UUID conselhoParticularId) {
        return jpaRepository.findByConselhoParticularId(conselhoParticularId).stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }
}
