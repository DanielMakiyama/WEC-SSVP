package com.daniel.wec_ssvp.adapter.out.persistence;

import com.daniel.wec_ssvp.adapter.out.persistence.mapper.PersistenceMapper;
import com.daniel.wec_ssvp.adapter.out.persistence.repository.UsuarioJpaRepository;
import com.daniel.wec_ssvp.application.port.out.UsuarioRepositoryPort;
import com.daniel.wec_ssvp.domain.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/** Adaptador de saída: implementa a porta de usuários com Spring Data JPA. */
@Component
@Transactional
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return PersistenceMapper.toDomain(
                jpaRepository.save(PersistenceMapper.toEntity(usuario))
        );
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email).map(PersistenceMapper::toDomain);
    }
}
