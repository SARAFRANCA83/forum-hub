package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.TopicoForm;
import br.com.sfranca.forum.hub.model.Topico;
import br.com.sfranca.forum.hub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid TopicoForm form) {

        boolean topicoExiste = topicoRepository
                .findByTituloAndMensagem(form.getTitulo(), form.getMensagem())
                .isPresent();

        if (topicoExiste) {
            return ResponseEntity.badRequest().body("Tópico duplicado: título e mensagem já existem.");
        }

        Topico topico = new Topico();
        topico.setTitulo(form.getTitulo());
        topico.setMensagem(form.getMensagem());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setAutor(form.getNomeAutor());
        topico.setCurso(form.getNomeCurso());

        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico criado com sucesso!");
    }
}

