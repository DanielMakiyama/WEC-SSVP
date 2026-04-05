package com.daniel.wec_ssvp.service.assistido;

import com.daniel.wec_ssvp.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.model.dto.BuscaComumDTO;
import com.daniel.wec_ssvp.model.dto.BuscaPorIdDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoUsuarioResponseDTO;
import com.daniel.wec_ssvp.model.entity.Assistido;
import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import com.daniel.wec_ssvp.repository.AssistidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListarAssistidoService {

    private final AssistidoRepository repository;

    public ListarAssistidoService(AssistidoRepository repository) {
        this.repository = repository;
    }

    public List<BuscaComumDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toResumoDTO)
                .toList();
    }

    public BuscaPorIdDTO buscarPorId(UUID id) {
        Assistido assistido = repository.findById(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);

        return toDetalheDTO(assistido);
    }

    private BuscaComumDTO toResumoDTO(Assistido assistido) {
        return new BuscaComumDTO(
                assistido.getId(),
                assistido.getNome(),
                assistido.getDataNascimento(),
                assistido.getAtivo(),
                assistido.getConferencia().getNome()
        );
    }

    private  BuscaPorIdDTO toDetalheDTO(Assistido assistido) {
        return new  BuscaPorIdDTO(
                assistido.getId(),
                assistido.getNome(),
                assistido.getConjuge(),
                assistido.getEndereco(),
                assistido.getDataNascimento(),
                assistido.getEstadoCivil(),
                assistido.getReligiao(),
                assistido.getProfissao(),
                assistido.getSituacaoMoradia(),
                assistido.getQuantidadeTrabalhadores(),
                assistido.getRendaFamiliar(),
                assistido.getRendaLiquida(),
                assistido.getValorAluguel(),
                assistido.getQuantidadeAlfabetizados(),
                assistido.getSituacaoCatequeseCrisma(),
                assistido.getParticipacaoIgrejaCatolica(),
                assistido.getProblemaSaude(),
                assistido.getOutrasInformacoes(),
                assistido.getAtivo(),
                assistido.getConferencia().getNome()
        );
    }
}
