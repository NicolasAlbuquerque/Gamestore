package com.generation.games.repository;

import com.generation.games.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){

        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "Ariane Albuquerque", "ariane@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));

        usuarioRepository.save(new Usuario(0L, "Valdir Albuquerque", "valdir@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));

        usuarioRepository.save(new Usuario(0L, "Elisabett Albuquerque", "elisabett@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Edilma Oliveira", "edilma@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario(){

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("ariane@email.com.br");
        assertTrue(usuario.get().getUsuario().equals("ariane@email.com.br"));
    }


    @Test
    @DisplayName("Retorna 3 Usuarios")
    public void deveRetornarTresuarios(){

        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Albuquerque");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("Ariane Albuquerque"));
        assertTrue(listaDeUsuarios.get(0).getNome().equals("Valdir Albuquerque"));
        assertTrue(listaDeUsuarios.get(0).getNome().equals("Elisabett Albuquerque"));
    }

    @AfterAll
    public void end(){
        usuarioRepository.deleteAll();
    }
}
