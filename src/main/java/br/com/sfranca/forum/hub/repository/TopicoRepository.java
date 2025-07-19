package br.com.sfranca.forum.hub.repository;

import br.com.sfranca.forum.hub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    //Page<Topico> findByCursoNomeAndDataCriacaoYear(String nomeCurso, Integer ano, Pageable pageable);

    //Page<Topico> findByCursoNome(String nomeCurso, Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND YEAR(t.dataCriacao) = :ano")
    Page<Topico> buscarPorCursoENoAno(@Param("nomeCurso") String nomeCurso, @Param("ano") int ano, Pageable pageable);


    //Page<Topico> findByCursoNomeAndAnoCriacao(String nomeCurso, Integer ano, Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND FUNCTION('YEAR', t.dataCriacao) = :ano")
    Page<Topico> findByCursoNomeAndAno(@Param("nomeCurso") String nomeCurso, @Param("ano") Integer ano, Pageable pageable);


}
