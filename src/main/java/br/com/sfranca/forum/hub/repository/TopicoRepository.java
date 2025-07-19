package br.com.sfranca.forum.hub.repository;

import br.com.sfranca.forum.hub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verifica se já existe um tópico com o mesmo título e mensagem
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}

