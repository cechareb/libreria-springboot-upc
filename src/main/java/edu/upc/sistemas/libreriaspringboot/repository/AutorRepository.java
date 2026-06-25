package edu.upc.sistemas.libreriaspringboot.repository;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Integer> {
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByNacionalidadContainingIgnoreCase(String nacionalidad);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.id = :id")
    Optional<Autor> findByIdWithLibros(@Param("id") Integer id);

    @Query("SELECT a.nombre, COUNT(1) FROM Autor a LEFT JOIN a.libros l GROUP BY a.nombre")
    List<Object[]> contarLibrosPorAutor();

    List<Autor> findByNacionalidadIgnoreCase(String nacionalidad);
}
