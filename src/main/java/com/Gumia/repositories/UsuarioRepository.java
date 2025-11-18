package com.Gumia.repositories;

import com.Gumia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuarios por nombre
    List<Usuario> findByNombre(String nombre);

    // Buscar usuario por email
    Usuario findByEmail(String email);

    // Comprobar si existe un usuario con ese nombre
    boolean existsByNombre(String nombre);

    // Comprobar si existe un usuario con ese email
    boolean existsByEmail(String email);
}
