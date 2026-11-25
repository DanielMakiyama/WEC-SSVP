package com.daniel.wec_ssvp.adapter.out.persistence.mapper;

import com.daniel.wec_ssvp.adapter.out.persistence.entity.AssistidoEntity;
import com.daniel.wec_ssvp.adapter.out.persistence.entity.ConferenciaEntity;
import com.daniel.wec_ssvp.adapter.out.persistence.entity.ConselhoParticularEntity;
import com.daniel.wec_ssvp.adapter.out.persistence.entity.UsuarioEntity;
import com.daniel.wec_ssvp.domain.model.Assistido;
import com.daniel.wec_ssvp.domain.model.Conferencia;
import com.daniel.wec_ssvp.domain.model.ConselhoParticular;
import com.daniel.wec_ssvp.domain.model.DadosAssistido;
import com.daniel.wec_ssvp.domain.model.Usuario;

/**
 * Converte entre as entidades JPA (tabelas) e o modelo de domínio.
 * É o único lugar que conhece os dois lados.
 */
public final class PersistenceMapper {

    private PersistenceMapper() {
    }

    // ---------- Conselho Particular ----------

    public static ConselhoParticular toDomain(ConselhoParticularEntity e) {
        return new ConselhoParticular(
                e.getId(),
                e.getNome(),
                e.getCidade(),
                e.getDataFundacao(),
                Boolean.TRUE.equals(e.getAtivo())
        );
    }

    public static ConselhoParticularEntity toEntity(ConselhoParticular c) {
        ConselhoParticularEntity e = new ConselhoParticularEntity();
        e.setId(c.getId());
        e.setNome(c.getNome());
        e.setCidade(c.getCidade());
        e.setDataFundacao(c.getDataFundacao());
        e.setAtivo(c.isAtivo());
        return e;
    }

    // ---------- Conferência ----------

    public static Conferencia toDomain(ConferenciaEntity e) {
        return new Conferencia(
                e.getId(),
                e.getNome(),
                Boolean.TRUE.equals(e.getAtivo()),
                toDomain(e.getConselhoParticular())
        );
    }

    /** O vínculo com o Conselho é preenchido pelo adaptador, que já tem a referência gerenciada. */
    public static ConferenciaEntity toEntity(Conferencia c) {
        ConferenciaEntity e = new ConferenciaEntity();
        e.setId(c.getId());
        e.setNome(c.getNome());
        e.setAtivo(c.isAtivo());
        return e;
    }

    // ---------- Assistido ----------

    public static Assistido toDomain(AssistidoEntity e) {
        DadosAssistido dados = new DadosAssistido(
                e.getNome(),
                e.getConjuge(),
                e.getEndereco(),
                e.getDataNascimento(),
                e.getEstadoCivil(),
                e.getReligiao(),
                e.getProfissao(),
                e.getSituacaoMoradia(),
                e.getQuantidadeTrabalhadores(),
                e.getRendaFamiliar(),
                e.getRendaLiquida(),
                e.getValorAluguel(),
                e.getQuantidadeAlfabetizados(),
                e.getSituacaoCatequeseCrisma(),
                e.getParticipacaoIgrejaCatolica(),
                e.getProblemaSaude(),
                e.getOutrasInformacoes()
        );
        return new Assistido(
                e.getId(),
                dados,
                toDomain(e.getConferencia()),
                Boolean.TRUE.equals(e.getAtivo())
        );
    }

    /** O vínculo com a Conferência é preenchido pelo adaptador, que já tem a referência gerenciada. */
    public static AssistidoEntity toEntity(Assistido a) {
        DadosAssistido d = a.getDados();
        AssistidoEntity e = new AssistidoEntity();
        e.setId(a.getId());
        e.setNome(d.nome());
        e.setConjuge(d.conjuge());
        e.setEndereco(d.endereco());
        e.setDataNascimento(d.dataNascimento());
        e.setEstadoCivil(d.estadoCivil());
        e.setReligiao(d.religiao());
        e.setProfissao(d.profissao());
        e.setSituacaoMoradia(d.situacaoMoradia());
        e.setQuantidadeTrabalhadores(d.quantidadeTrabalhadores());
        e.setRendaFamiliar(d.rendaFamiliar());
        e.setRendaLiquida(d.rendaLiquida());
        e.setValorAluguel(d.valorAluguel());
        e.setQuantidadeAlfabetizados(d.quantidadeAlfabetizados());
        e.setSituacaoCatequeseCrisma(d.situacaoCatequeseCrisma());
        e.setParticipacaoIgrejaCatolica(d.participacaoIgrejaCatolica());
        e.setProblemaSaude(d.problemaSaude());
        e.setOutrasInformacoes(d.outrasInformacoes());
        e.setAtivo(a.isAtivo());
        return e;
    }

    // ---------- Usuário ----------

    public static Usuario toDomain(UsuarioEntity e) {
        return new Usuario(
                e.getId(),
                e.getEmail(),
                e.getSenha(),
                e.getTipoUsuario(),
                Boolean.TRUE.equals(e.getAtivo())
        );
    }

    public static UsuarioEntity toEntity(Usuario u) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setEmail(u.getEmail());
        e.setSenha(u.getSenhaHash());
        e.setTipoUsuario(u.getTipoUsuario());
        e.setAtivo(u.isAtivo());
        return e;
    }
}
