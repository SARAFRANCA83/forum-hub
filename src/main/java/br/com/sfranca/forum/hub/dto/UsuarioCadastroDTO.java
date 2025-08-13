package br.com.sfranca.forum.hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDTO(
        @NotBlank @Size(min = 3, max = 100) String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String senha
) {}
