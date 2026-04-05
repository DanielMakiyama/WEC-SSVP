package com.daniel.wec_ssvp.service.assistido;

import com.daniel.wec_ssvp.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.model.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.model.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.model.entity.Assistido;
import com.daniel.wec_ssvp.repository.AssistidoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarAssistidoService {

    private final AssistidoRepository assistidoRepository;

    public DeletarAssistidoService(AssistidoRepository assistidoRepository) {
        this.assistidoRepository = assistidoRepository;
    }

    public DeleteResponseDTO deleteById(UUID id){

        Assistido assistido = assistidoRepository.findById(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);

        assistido.setAtivo(false);

        Assistido salvo = assistidoRepository.save(assistido);

        return toResponseDTO(salvo);
    }

    private DeleteResponseDTO toResponseDTO(Assistido assistido) {
        return DeleteResponseDTO.criado(
                assistido.getId(),
                assistido.getNome(),
                assistido.getAtivo(),
                assistido.getMessage()
        );
    }
}