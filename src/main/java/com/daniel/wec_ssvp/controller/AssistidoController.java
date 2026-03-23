package com.daniel.wec_ssvp.controller;

import com.daniel.wec_ssvp.model.Assistido;
import com.daniel.wec_ssvp.service.AssistidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.daniel.wec_ssvp.dto.AssistidoRequestDTO;
import com.daniel.wec_ssvp.dto.AssistidoResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/assistidos")
public class AssistidoController{

    @Autowired
    private AssistidoService assistidoService;

    //POST
    @PostMapping
    public ResponseEntity<Assistido> creat(@RequestBody AssistidoRequestDTO dto){
        Assistido savedAssistido = assistidoService.createFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssistido);
    }

    //FIND ALL
    @GetMapping
    public ResponseEntity<List<AssistidoResponseDTO>> findAll(){
        return ResponseEntity.ok(assistidoService.findAll());
    }

    //FIND BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AssistidoResponseDTO> findById(@PathVariable Integer id){
        return assistidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<AssistidoResponseDTO> update(@PathVariable Integer id, @RequestBody AssistidoRequestDTO dto){
        try{
            AssistidoResponseDTO updateAssistido = assistidoService.update(id, dto);
            return ResponseEntity.ok(updateAssistido);
        }
        catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        assistidoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 (No Content) - sucesso sem corpo de resposta
    }


}
