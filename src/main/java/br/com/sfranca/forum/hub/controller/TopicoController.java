package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.TopicoRequestDTO;
import br.com.sfranca.forum.hub.dto.TopicoResponseDTO;
import br.com.sfranca.forum.hub.dto.TopicoUpdateDTO;
import br.com.sfranca.forum.hub.model.Curso;
import br.com.sfranca.forum.hub.model.Topico;
import br.com.sfranca.forum.hub.model.Usuario;
import br.com.sfranca.forum.hub.repository.CursoRepository;
import br.com.sfranca.forum.hub.repository.TopicoRepository;
import br.com.sfranca.forum.hub.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<TopicoResponseDTO> cadastrar(@RequestBody @Valid TopicoRequestDTO dto) {
        if (topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario autor = usuarioRepository.findById(dto.autorId()).orElseThrow();
        Curso curso = cursoRepository.findById(dto.cursoId()).orElseThrow();

        Topico topico = new Topico();
        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topicoRepository.save(topico);

        TopicoResponseDTO response = new TopicoResponseDTO(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano,
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao
    ) {
        Page<Topico> topicos;

        if (nomeCurso != null && ano != null) {
            topicos = topicoRepository.findByCursoNomeAndAno(nomeCurso, ano, paginacao);
        } else if (nomeCurso != null) {
            topicos = topicoRepository.buscarPorCursoENoAno(nomeCurso, ano, paginacao);
        } else {
            topicos = topicoRepository.findAll(paginacao);
        }

        Page<TopicoResponseDTO> dto = topicos.map(topico -> new TopicoResponseDTO(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        ));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow();

        TopicoResponseDTO dto = new TopicoResponseDTO(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        );

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoUpdateDTO dto) {
        Topico topicoExistente = topicoRepository.findById(id).orElseThrow();

        boolean existeOutroTopicoIgual = topicoRepository.existsByTituloAndMensagem(dto.titulo(), dto.mensagem())
                && (!topicoExistente.getTitulo().equals(dto.titulo()) || !topicoExistente.getMensagem().equals(dto.mensagem()));

        if (existeOutroTopicoIgual) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario autor = usuarioRepository.findById(dto.autorId()).orElseThrow();
        Curso curso = cursoRepository.findById(dto.cursoId()).orElseThrow();

        topicoExistente.setTitulo(dto.titulo());
        topicoExistente.setMensagem(dto.mensagem());
        topicoExistente.setAutor(autor);
        topicoExistente.setCurso(curso);

        topicoRepository.save(topicoExistente);

        return ResponseEntity.ok(topicoExistente);
    }
}
