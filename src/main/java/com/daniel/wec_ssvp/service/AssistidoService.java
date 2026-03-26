package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.dto.AssistidoRequestDTO;
import com.daniel.wec_ssvp.dto.AssistidoResponseDTO;
import com.daniel.wec_ssvp.entity.Assistido;
import com.daniel.wec_ssvp.entity.Conferencia; // Import necessário
import com.daniel.wec_ssvp.repository.AssistidoRepository;
import com.daniel.wec_ssvp.repository.ConferenciaRepository; // Import necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssistidoService {

    @Autowired
    private AssistidoRepository assistidoRepository;

    @Autowired
    private ConferenciaRepository conferenciaRepository; // <-- CORRIGIDO O TIPO AQUI

    // Mudei o retorno para ResponseDTO para ficar no padrão das outras classes
    public AssistidoResponseDTO createFromDTO(AssistidoRequestDTO dto){

        // Busca a conferência no banco para garantir que ela existe
        Conferencia conferencia = conferenciaRepository.findById(dto.conferenciaId())
                .orElseThrow(() -> new RuntimeException("Conferência não encontrada"));

        Assistido newAssistido = new Assistido();

        newAssistido.setNome(dto.nome());
        newAssistido.setConjuge(dto.conjuge());
        newAssistido.setEndereco(dto.endereco());
        newAssistido.setDataNascimento(dto.dataNascimento());
        newAssistido.setEstadoCivil(dto.estadoCivil());
        newAssistido.setReligiao(dto.religiao());
        newAssistido.setSituacaoMoradia(dto.situacaoMoradia());
        newAssistido.setProfissao(dto.profissao());
        newAssistido.setQuantidadeTrabalhadores(dto.quantidadeTrabalhadores());
        newAssistido.setRendaFamiliar(dto.rendaFamiliar());
        newAssistido.setRendaLiquida(dto.rendaLiquida());
        newAssistido.setValorAluguel(dto.valorAluguel());
        newAssistido.setQuantidadeAlfabetizados(dto.quantidadeAlfabetizados());
        newAssistido.setSituacaoCatequeseCrisma(dto.situacaoCatequeseCrisma());
        newAssistido.setParticipacaoIgrejaCatolica(dto.participacaoIgrejaCatolica());
        newAssistido.setProblemaSaude(dto.problemaSaude());
        newAssistido.setOutrasInformacoes(dto.outrasInformacoes());

        // Faz a amarração com a conferência
        newAssistido.setConferencia(conferencia);

        Assistido salvo = assistidoRepository.save(newAssistido);
        return toResponseDTO(salvo);
    }

    public List<AssistidoResponseDTO> findAll(){
        return assistidoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<AssistidoResponseDTO> findById(UUID id){
        return assistidoRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public AssistidoResponseDTO update(UUID id, AssistidoRequestDTO dto){

        Assistido existingAssistido = assistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));

        // Busca a conferência na hora de atualizar
        Conferencia conferencia = conferenciaRepository.findById(dto.conferenciaId())
                .orElseThrow(() -> new RuntimeException("Conferência não encontrada"));

        existingAssistido.setNome(dto.nome());
        existingAssistido.setConjuge(dto.conjuge());
        existingAssistido.setEndereco(dto.endereco());
        existingAssistido.setDataNascimento(dto.dataNascimento());
        existingAssistido.setEstadoCivil(dto.estadoCivil());
        existingAssistido.setReligiao(dto.religiao());
        existingAssistido.setSituacaoMoradia(dto.situacaoMoradia());
        existingAssistido.setProfissao(dto.profissao());
        existingAssistido.setQuantidadeTrabalhadores(dto.quantidadeTrabalhadores());
        existingAssistido.setRendaFamiliar(dto.rendaFamiliar());
        existingAssistido.setRendaLiquida(dto.rendaLiquida());
        existingAssistido.setValorAluguel(dto.valorAluguel());
        existingAssistido.setQuantidadeAlfabetizados(dto.quantidadeAlfabetizados());
        existingAssistido.setSituacaoCatequeseCrisma(dto.situacaoCatequeseCrisma());
        existingAssistido.setParticipacaoIgrejaCatolica(dto.participacaoIgrejaCatolica());
        existingAssistido.setProblemaSaude(dto.problemaSaude());
        existingAssistido.setOutrasInformacoes(dto.outrasInformacoes());

        //Atualiza a amarração da conferência
        existingAssistido.setConferencia(conferencia);

        Assistido updateAssistido = assistidoRepository.save(existingAssistido);
        return toResponseDTO(updateAssistido);
    }

    public void deleteById(UUID id){
        assistidoRepository.deleteById(id);
    }

    public AssistidoResponseDTO toResponseDTO(Assistido assistido) {
        return new AssistidoResponseDTO(
                assistido.getId(),
                assistido.getNome(),
                assistido.getConjuge(),
                assistido.getEndereco(),
                assistido.getDataNascimento(),
                assistido.getEstadoCivil(),
                assistido.getReligiao(),
                assistido.getSituacaoMoradia(),
                assistido.getProfissao(),
                assistido.getQuantidadeTrabalhadores(),
                assistido.getRendaFamiliar(),
                assistido.getRendaLiquida(),
                assistido.getValorAluguel(),
                assistido.getQuantidadeAlfabetizados(),
                assistido.getSituacaoCatequeseCrisma(),
                assistido.getParticipacaoIgrejaCatolica(),
                assistido.getProblemaSaude(),
                assistido.getOutrasInformacoes(),
                assistido.getConferencia().getId(),
                assistido.getConferencia().getNome()
        );
    }
}