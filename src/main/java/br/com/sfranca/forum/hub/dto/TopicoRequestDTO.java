package br.com.sfranca.forum.hub.dto;

public record TopicoRequestDTO(
        String titulo,
        String mensagem,
        Long autorId,
        Long cursoId
) {}

