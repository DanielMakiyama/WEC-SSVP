package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.service.AssistidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.daniel.wec_ssvp.dto.AssistidoRequestDTO;
import com.daniel.wec_ssvp.dto.AssistidoResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assistidos")
public class AssistidoController{

    @Autowired
    private AssistidoService assistidoService;

    //POST
    @PostMapping("/criar")
    public ResponseEntity<AssistidoResponseDTO> create(@RequestBody AssistidoRequestDTO dto){
        AssistidoResponseDTO savedAssistido = assistidoService.createFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssistido);
    }

    //FIND ALL
    @GetMapping("/buscar")
    public ResponseEntity<List<AssistidoResponseDTO>> findAll(){
        return ResponseEntity.ok(assistidoService.findAll());
    }

    //FIND BY ID
    @GetMapping("/buscarId/{id}")
    public ResponseEntity<AssistidoResponseDTO> findById(@PathVariable UUID id){
        return assistidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //PUT
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AssistidoResponseDTO> update(@PathVariable UUID id, @RequestBody AssistidoRequestDTO dto){
        try{
            AssistidoResponseDTO updateAssistido = assistidoService.update(id, dto);
            return ResponseEntity.ok(updateAssistido);
        }
        catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        assistidoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 (No Content) - sucesso sem corpo de resposta
    }


}
