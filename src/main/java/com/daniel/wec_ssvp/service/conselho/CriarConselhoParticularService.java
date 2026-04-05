package com.daniel.wec_ssvp.service.conselho;

import com.daniel.wec_ssvp.exception.ConselhoJaExisteException;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.ConselhoParticularRequestDTO;
import com.daniel.wec_ssvp.model.entity.ConselhoParticular;
import com.daniel.wec_ssvp.model.entity.TipoReuniao;
import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import com.daniel.wec_ssvp.repository.ConselhoParticularRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarConselhoParticularService {

    private final ConselhoParticularRepository repository;

    public CriarConselhoParticularService(
            ConselhoParticularRepository repository) {
        this.repository = repository;
    }

    public CriacaoReuniaoResponseDTO executar(
            ConselhoParticularRequestDTO dto) {

        repository.findByNome(dto.nome())
                .ifPresent(conselhoExistente -> {
                    throw new ConselhoJaExisteException();
                });

        ConselhoParticular conselho = new ConselhoParticular();
        conselho.setNome(dto.nome());
        conselho.setCidade(dto.cidade());
        conselho.setDataFundacao(dto.dataFundacao());
        conselho.setAtivo(true);

        ConselhoParticular salvo = repository.save(conselho);

        return toResponseDTO(salvo);
    }

    private CriacaoReuniaoResponseDTO toResponseDTO(
            ConselhoParticular conselho) {

        return CriacaoReuniaoResponseDTO.criado(
                conselho.getId(),
                conselho.getNome(),
                TipoReuniao.CONSELHO_PARTICULAR
        );
    }
}
