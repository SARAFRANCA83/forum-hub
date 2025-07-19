package br.com.sfranca.forum.hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para receber dados de criação de um tópico.
 */
public record TopicoRequestDTO(
        @NotBlank(message = "O título não pode estar em branco")
        String titulo,

        @NotBlank(message = "A mensagem não pode estar em branco")
        String mensagem,

        @NotNull(message = "O ID do autor é obrigatório")
        Long autorId,

        @NotNull(message = "O ID do curso é obrigatório")
        Long cursoId
) {}

