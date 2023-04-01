package com.generation.games.controller;

import com.generation.games.model.Usuario;
import com.generation.games.repository.UsuarioRepository;
import com.generation.games.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", " "));
    }


    @Test
    @DisplayName("Cadastrar um Usuario")
    public void deveCriarUmUsuario() {

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Nicolas Albuquerque", "nicolas_albuquerque@email.com.br", "12345678", "http://i.imgur.com/JR7kUFU.jpg"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("Não deve permitir a duplicação do usuário")
    public void naoDeveDuplicarUsuario() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Maria Edilma", "maria_edilma@email.com.br", "12345678", "hhtp://i.imgur.com.T12NIp9.jpg"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Maria Edilma", "maria_edilma@email.com.br", "12345678", "hhtp://i.imgur.com.T12NIp9.jpg"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário.")
    public void deveAtualizarUmUsuario() {

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, "Horacio Doguinho", "horacio_dog@email.com.br", "123445678", "hhtp://i.imgur.com.T12NIp9.jpg"));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                "Horacitto doguitto", "horacitto_dogguitto@email.com", "horacio2", "hhtp://i.imgur.com.T12NIp9.jpg");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("Listar todos os Usuarios")
    public void deveMostrarTodosUsuarios(){

        usuarioService.cadastrarUsuario(new Usuario(0L, "Teobaldo gatito", "teobaldo_felino@email.com.br","12345678", "hhtp://i.imgur.com.T12NIp9.jpg"));

        usuarioService.cadastrarUsuario(new Usuario(0L,"Coockie Cadelinha", "coockie_cadelinha@email.com.br", "12345678","hhtp://i.imgur.com.T12NIp9.jpg"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                    .exchange("/usuarios/all", HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}