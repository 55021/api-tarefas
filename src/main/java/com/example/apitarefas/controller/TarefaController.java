package com.example.apitarefas.controller;

import com.example.apitarefas.model.Tarefa;
import com.example.apitarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Tarefa> adicionarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);
        if (tarefaExistente.isPresent()) {
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setNome(tarefaAtualizada.getNome());
            tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
            tarefa.setResponsavel(tarefaAtualizada.getResponsavel());
            Tarefa tarefaSalva = tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefaSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}