package com.daniel.wec_ssvp.service;

import com.daniel.wec_ssvp.dto.AssistidoRequestDTO;
import com.daniel.wec_ssvp.dto.AssistidoResponseDTO;
import com.daniel.wec_ssvp.repository.AssistidoRepository;
import com.daniel.wec_ssvp.model.Assistido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AssistidoService {

    @Autowired
    private AssistidoRepository assistidoRepository;

    public Assistido createFromDTO(AssistidoRequestDTO dto){
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

        return assistidoRepository.save(newAssistido);
    }

    public List<AssistidoResponseDTO> findAll(){
        return assistidoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Optional<AssistidoResponseDTO> findById(Integer id){
        return assistidoRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public AssistidoResponseDTO update(Integer id, AssistidoRequestDTO dto){

        Assistido existingAssistido = assistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));

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

        Assistido updateAssistido = assistidoRepository.save(existingAssistido);
        return toResponseDTO(updateAssistido);
    }

    public void deleteById(Integer id){
        assistidoRepository.deleteById(id);
    }

    public AssistidoResponseDTO toResponseDTO(Assistido assistido) {
        return new AssistidoResponseDTO(
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
                assistido.getOutrasInformacoes()
        );
    }


}
