package br.com.sfranca.forum.hub.repository;

import br.com.sfranca.forum.hub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
