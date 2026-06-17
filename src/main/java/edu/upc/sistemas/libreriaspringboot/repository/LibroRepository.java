package edu.upc.sistemas.libreriaspringboot.repository;

import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Integer> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    Libro findByIsbn(String isbn);

    List<Libro> findByAutorId(Integer autorId);

    List<Libro> findByPrecioGreaterThan(BigDecimal precio);

    @Query(value = """
        SELECT l.id, l.titulo, l.isbn, l.anio_publicacion, l.precio,
               a.id as autor_id, a.nombre as autor_nombre, a.nacionalidad
        FROM libros l
        JOIN autores a ON l.autor_id = a.id
        WHERE l.anio_publicacion >= :anio
        """, nativeQuery = true)
    List<Object[]> findLibrosConAutorDesdeAnio(@Param("anio") Integer anio);

    @Query("SELECT a.nombre, AVG(l.precio) FROM Libro l JOIN l.autor a GROUP BY a.nombre")
    List<Object[]> promedioPrecioPorAutor();
}
