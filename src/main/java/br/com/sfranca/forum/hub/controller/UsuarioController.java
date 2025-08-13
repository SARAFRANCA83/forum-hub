package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.UsuarioCadastroDTO;
import br.com.sfranca.forum.hub.model.Usuario;
import br.com.sfranca.forum.hub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioCadastroDTO dto) {
        // Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(senhaCriptografada);

        Usuario salvo = usuarioRepository.save(usuario);

        return ResponseEntity.ok(salvo);
    }
}
