package br.com.sfranca.forum.hub.repository;

import br.com.sfranca.forum.hub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);  // Método para buscar usuário por email
}
