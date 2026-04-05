package com.daniel.wec_ssvp.service.conferencia;

import com.daniel.wec_ssvp.exception.ConferenciaJaExisteException;
import com.daniel.wec_ssvp.exception.ConselhoParticularNaoEncontradoException;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.ConferenciaRequestDTO;
import com.daniel.wec_ssvp.model.entity.Conferencia;
import com.daniel.wec_ssvp.model.entity.ConselhoParticular;
import com.daniel.wec_ssvp.model.entity.TipoReuniao;
import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import com.daniel.wec_ssvp.repository.ConferenciaRepository;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarConferenciaService {

    private final ConferenciaRepository conferenciaRepository;
    private final ConselhoParticularRepository conselhoRepository;

    public CriarConferenciaService(
            ConferenciaRepository conferenciaRepository,
            ConselhoParticularRepository conselhoRepository) {

        this.conferenciaRepository = conferenciaRepository;
        this.conselhoRepository = conselhoRepository;
    }

    public CriacaoReuniaoResponseDTO salvar(ConferenciaRequestDTO dto) {

        ConselhoParticular conselho = conselhoRepository
                .findById(dto.conselhoParticularId())
                .orElseThrow(ConselhoParticularNaoEncontradoException::new);

        conferenciaRepository
                .findByNomeAndConselhoParticularId(
                        dto.nome(),
                        dto.conselhoParticularId()
                )
                .ifPresent(c -> {
                    throw new ConferenciaJaExisteException();
                });

        Conferencia conferencia = new Conferencia();
        conferencia.setNome(dto.nome());
        conferencia.setAtivo(true);
        conferencia.setConselhoParticular(conselho);

        Conferencia salva = conferenciaRepository.save(conferencia);

        return toResponseDTO(salva);
    }

    private CriacaoReuniaoResponseDTO toResponseDTO(Conferencia conferencia) {
        return CriacaoReuniaoResponseDTO.criado(
                conferencia.getId(),
                conferencia.getNome(),
                TipoReuniao.CONFERENCIA
        );
    }
}
