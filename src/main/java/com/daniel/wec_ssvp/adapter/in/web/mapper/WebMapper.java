package com.daniel.wec_ssvp.adapter.in.web.mapper;

import com.daniel.wec_ssvp.adapter.in.web.dto.BuscaComumDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.BuscaPorIdDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.ConsultaResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.CriacaoReuniaoResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.CriacaoUsuarioResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.DeleteResponseDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.TipoReuniao;
import com.daniel.wec_ssvp.adapter.in.web.dto.cadastro.AssistidoRequestDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.DadosPessoaisDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.SituacaoFamiliarDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.SituacaoIgrejaSaudeDTO;
import com.daniel.wec_ssvp.application.port.in.assistido.AssistidoCommand;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import com.daniel.wec_ssvp.domain.model.DadosAssistido;
import com.daniel.wec_ssvp.domain.model.TipoUsuario;

/**
 * Converte entre os DTOs da API REST e o modelo de domínio.
 * Mantém o formato JSON da API independente das classes do núcleo.
 */
public final class WebMapper {

    private WebMapper() {
    }

    // ---------- Requisições ----------

    public static AssistidoCommand toCommand(AssistidoRequestDTO dto) {
        DadosPessoaisDTO p = dto.dadosPessoais();
        SituacaoFamiliarDTO f = dto.situacaoFamiliar();
        SituacaoIgrejaSaudeDTO s = dto.situacaoIgrejaSaude();

        DadosAssistido dados = new DadosAssistido(
                p.nome(),
                p.conjuge(),
                p.endereco(),
                p.dataNascimento(),
                p.estadoCivil(),
                p.religiao(),
                p.profissao(),
                f.situacaoMoradia(),
                f.quantidadeTrabalhadores(),
                f.rendaFamiliar(),
                f.rendaLiquida(),
                f.valorAluguel(),
                f.quantidadeAlfabetizados(),
                s.situacaoCatequeseCrisma(),
                s.participacaoIgrejaCatolica(),
                s.problemaSaude(),
                s.outrasInformacoes()
        );

        return new AssistidoCommand(dados, dto.conferenciaId());
    }

    // ---------- Respostas de Assistido ----------

    public static CriacaoUsuarioResponseDTO toCriacaoResponse(Assistido assistido) {
        return CriacaoUsuarioResponseDTO.criado(
                assistido.getId(),
                assistido.getNome(),
                TipoUsuario.BENEFICIARIO
        );
    }

    public static BuscaComumDTO toResumo(Assistido assistido) {
        return new BuscaComumDTO(
                assistido.getId(),
                assistido.getNome(),
                assistido.getDados().dataNascimento(),
                assistido.isAtivo(),
                assistido.getConferencia().getNome()
        );
    }

    public static BuscaPorIdDTO toDetalhe(Assistido assistido) {
        DadosAssistido d = assistido.getDados();
        return new BuscaPorIdDTO(
                assistido.getId(),
                d.nome(),
                d.conjuge(),
                d.endereco(),
                d.dataNascimento(),
                d.estadoCivil(),
                d.religiao(),
                d.profissao(),
                d.situacaoMoradia(),
                d.quantidadeTrabalhadores(),
                d.rendaFamiliar(),
                d.rendaLiquida(),
                d.valorAluguel(),
                d.quantidadeAlfabetizados(),
                d.situacaoCatequeseCrisma(),
                d.participacaoIgrejaCatolica(),
                d.problemaSaude(),
                d.outrasInformacoes(),
                assistido.isAtivo(),
                assistido.getConferencia().getNome()
        );
    }

    public static DeleteResponseDTO toDeleteResponse(Assistido assistido) {
        return DeleteResponseDTO.criado(assistido.getId(), assistido.getNome(), assistido.isAtivo(), null);
    }

    // ---------- Respostas de Conferência ----------

    public static CriacaoReuniaoResponseDTO toCriacaoResponse(Conferencia conferencia) {
        return CriacaoReuniaoResponseDTO.criado(
                conferencia.getId(),
                conferencia.getNome(),
                TipoReuniao.CONFERENCIA
        );
    }

    public static ConsultaResponseDTO toConsulta(Conferencia conferencia) {
        return new ConsultaResponseDTO(conferencia.getId(), conferencia.getNome(), conferencia.isAtivo());
    }

    public static DeleteResponseDTO toDeleteResponse(Conferencia conferencia) {
        return DeleteResponseDTO.criado(conferencia.getId(), conferencia.getNome(), conferencia.isAtivo(), null);
    }

    // ---------- Respostas de Conselho Particular ----------

    public static CriacaoReuniaoResponseDTO toCriacaoResponse(ConselhoParticular conselho) {
        return CriacaoReuniaoResponseDTO.criado(
                conselho.getId(),
                conselho.getNome(),
                TipoReuniao.CONSELHO_PARTICULAR
        );
    }

    public static ConsultaResponseDTO toConsulta(ConselhoParticular conselho) {
        return new ConsultaResponseDTO(conselho.getId(), conselho.getNome(), conselho.isAtivo());
    }

    public static DeleteResponseDTO toDeleteResponse(ConselhoParticular conselho) {
        return DeleteResponseDTO.criado(conselho.getId(), conselho.getNome(), conselho.isAtivo(), null);
    }
}
