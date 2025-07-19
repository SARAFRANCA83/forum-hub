package br.com.sfranca.forum.hub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos") // Define nome explícito da tabela
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String categoria;
}
