package br.com.sfranca.forum.hub.controller;

import br.com.sfranca.forum.hub.dto.DadosAutenticacao;
import br.com.sfranca.forum.hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService; // Injeta o serviço de token

    @PostMapping
    public ResponseEntity<Map<String, String>> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria o token de autenticação a partir do login e senha
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        // Autentica o usuário
        Authentication authentication = manager.authenticate(authToken);

        // Gera o token JWT
        String jwt = tokenService.gerarToken(authentication);

        // Retorna o token em um JSON
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
