package edu.upc.sistemas.libreriaspringboot.repository;

import edu.upc.sistemas.libreriaspringboot.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
}
