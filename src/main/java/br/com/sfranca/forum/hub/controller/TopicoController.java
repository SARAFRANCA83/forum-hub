package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.TopicoRequestDTO;
import br.com.sfranca.forum.hub.dto.TopicoResponseDTO;
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
import org.springframework.web.server.ResponseStatusException;


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

        // Retorna como DTO
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
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));

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

}
