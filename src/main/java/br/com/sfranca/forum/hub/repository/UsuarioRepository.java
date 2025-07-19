package br.com.sfranca.forum.hub.repository;

import br.com.sfranca.forum.hub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

