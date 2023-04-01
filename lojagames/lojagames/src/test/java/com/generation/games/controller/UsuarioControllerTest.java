package com.generation.games.controller;

import com.generation.games.model.Usuario;
import com.generation.games.repository.UsuarioRepository;
import com.generation.games.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    void start(){
        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L,"Root","root@root.com", "rootroot", " "));
    }
}
