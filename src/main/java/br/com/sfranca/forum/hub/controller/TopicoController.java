package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.TopicoRequestDTO;
import br.com.sfranca.forum.hub.model.Curso;
import br.com.sfranca.forum.hub.model.Topico;
import br.com.sfranca.forum.hub.model.Usuario;
import br.com.sfranca.forum.hub.repository.CursoRepository;
import br.com.sfranca.forum.hub.repository.TopicoRepository;
import br.com.sfranca.forum.hub.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Topico> cadastrar(@RequestBody @Valid TopicoRequestDTO dto) {
        // Verifica se já existe um tópico com mesmo título e mensagem
        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            return ResponseEntity.badRequest().build();
        }

        // Busca autor e curso no banco
        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Cria e salva o tópico
        Topico topico = new Topico();
        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);

        topicoRepository.save(topico);

        return ResponseEntity.ok(topico);
    }
}
