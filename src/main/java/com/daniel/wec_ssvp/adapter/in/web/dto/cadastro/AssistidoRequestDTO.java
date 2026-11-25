package com.daniel.wec_ssvp.adapter.in.web.dto.cadastro;

import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.DadosPessoaisDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.SituacaoFamiliarDTO;
import com.daniel.wec_ssvp.adapter.in.web.dto.informacoesComplementares.SituacaoIgrejaSaudeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssistidoRequestDTO(
        @Valid
        DadosPessoaisDTO dadosPessoais,

        @Valid
        SituacaoFamiliarDTO situacaoFamiliar,

        @Valid
        SituacaoIgrejaSaudeDTO situacaoIgrejaSaude,

        @NotNull(message = "Conferência é obrigatória")
        UUID conferenciaId
) {}