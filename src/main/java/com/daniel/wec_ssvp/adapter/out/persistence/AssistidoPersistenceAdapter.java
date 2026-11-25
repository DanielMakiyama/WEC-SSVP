package com.daniel.wec_ssvp.adapter.out.persistence;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.AssistidoEntity;
import com.daniel.wec_ssvp.adapter.out.persistence.mapper.PersistenceMapper;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.AssistidoJpaRepository;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.ConferenciaJpaRepository;
import com.daniel.wec_ssvp.application.port.out.AssistidoRepositoryPort;
import com.daniel.wec_ssvp.domain.model.Assistido;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Adaptador de saída: implementa a porta de assistidos com Spring Data JPA. */
@Component
@Transactional
public class AssistidoPersistenceAdapter implements AssistidoRepositoryPort {

    private final AssistidoJpaRepository jpaRepository;
    private final ConferenciaJpaRepository conferenciaJpaRepository;

    public AssistidoPersistenceAdapter(AssistidoJpaRepository jpaRepository,
                                       ConferenciaJpaRepository conferenciaJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.conferenciaJpaRepository = conferenciaJpaRepository;
    }

    @Override
    public Assistido salvar(Assistido assistido) {
        AssistidoEntity entity = PersistenceMapper.toEntity(assistido);
        entity.setConferencia(
                conferenciaJpaRepository.getReferenceById(assistido.getConferencia().getId())
        );
        return PersistenceMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Assistido> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Assistido> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(PersistenceMapper::toDomain)
                .toList();
    }
}
