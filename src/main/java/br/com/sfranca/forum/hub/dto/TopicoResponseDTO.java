package br.com.sfranca.forum.hub.dto;

import br.com.sfranca.forum.hub.model.StatusTopico;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignora campos nulos no JSON
public record TopicoResponseDTO(
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso
) {}
