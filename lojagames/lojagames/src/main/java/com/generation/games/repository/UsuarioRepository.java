package com.generation.games.repository;

import com.generation.games.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByUsuario(String usuario);

    public List<Usuario> findAllByNomeContainingIgnoreCase(@Param("nome")String nome );
}
