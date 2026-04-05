package com.daniel.wec_ssvp.service.assistido;

import com.daniel.wec_ssvp.exception.AssistidoNaoEncontradoException;
import com.daniel.wec_ssvp.exception.ConferenciaNaoEncontradaException;
import com.daniel.wec_ssvp.model.dto.CriacaoUsuarioResponseDTO;
import com.daniel.wec_ssvp.model.dto.cadastro.AssistidoRequestDTO;
import com.daniel.wec_ssvp.model.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.model.entity.Assistido;
import com.daniel.wec_ssvp.model.entity.Conferencia;
import com.daniel.wec_ssvp.model.entity.TipoUsuario;
import com.daniel.wec_ssvp.repository.AssistidoRepository;
import com.daniel.wec_ssvp.repository.ConferenciaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarAssistidoService {

    private final AssistidoRepository assistidoRepository;
    private final ConferenciaRepository conferenciaRepository;

    public AtualizarAssistidoService(
            AssistidoRepository assistidoRepository,
            ConferenciaRepository conferenciaRepository) {
        this.assistidoRepository = assistidoRepository;
        this.conferenciaRepository = conferenciaRepository;
    }

    public CriacaoUsuarioResponseDTO executar(UUID id, AssistidoRequestDTO dto){

        Assistido assistido = assistidoRepository.findById(id)
                .orElseThrow(AssistidoNaoEncontradoException::new);

        Conferencia conferencia = conferenciaRepository.findById(dto.conferenciaId())
                .orElseThrow(ConferenciaNaoEncontradaException::new);

        map(dto, assistido);
        assistido.setConferencia(conferencia);

        return toResponseDTO(
                assistidoRepository.save(assistido)
        );
    }

    private void map(AssistidoRequestDTO dto, Assistido a){
        // Dados pessoais
        a.setNome(dto.dadosPessoais().nome());
        a.setConjuge(dto.dadosPessoais().conjuge());
        a.setEndereco(dto.dadosPessoais().endereco());
        a.setDataNascimento(dto.dadosPessoais().dataNascimento());
        a.setEstadoCivil(dto.dadosPessoais().estadoCivil());
        a.setReligiao(dto.dadosPessoais().religiao());
        a.setProfissao(dto.dadosPessoais().profissao());

        // Situação familiar
        a.setSituacaoMoradia(dto.situacaoFamiliar().situacaoMoradia());
        a.setQuantidadeTrabalhadores(dto.situacaoFamiliar().quantidadeTrabalhadores());
        a.setRendaFamiliar(dto.situacaoFamiliar().rendaFamiliar());
        a.setRendaLiquida(dto.situacaoFamiliar().rendaLiquida());
        a.setValorAluguel(dto.situacaoFamiliar().valorAluguel());
        a.setQuantidadeAlfabetizados(dto.situacaoFamiliar().quantidadeAlfabetizados());

        // Igreja e saúde
        a.setSituacaoCatequeseCrisma(dto.situacaoIgrejaSaude().situacaoCatequeseCrisma());
        a.setParticipacaoIgrejaCatolica(dto.situacaoIgrejaSaude().participacaoIgrejaCatolica());
        a.setProblemaSaude(dto.situacaoIgrejaSaude().problemaSaude());
        a.setOutrasInformacoes(dto.situacaoIgrejaSaude().outrasInformacoes());
    }

    private CriacaoUsuarioResponseDTO toResponseDTO(Assistido assistido) {

        return CriacaoUsuarioResponseDTO.criado(
                assistido.getId(),
                assistido.getNome(),
                TipoUsuario.BENEFICIARIO
        );
    }
}
