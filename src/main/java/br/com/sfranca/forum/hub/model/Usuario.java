package br.com.sfranca.forum.hub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios") // Nome explicativo para a tabela no banco
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;
}
