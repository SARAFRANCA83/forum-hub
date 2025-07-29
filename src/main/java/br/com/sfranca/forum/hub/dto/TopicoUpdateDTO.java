package br.com.sfranca.forum.hub.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record TopicoUpdateDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull Long autorId,
        @NotNull Long cursoId
) {

}

