package edu.upc.sistemas.libreriaspringboot.service;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;

import java.math.BigDecimal;
import java.util.List;

public interface LibroService {
    Libro crearLibro(Libro libro);
    Libro obtenerLibroPorId(Integer id);
    List<Libro> listarTodos();
    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> buscarPorAutor(Integer autorId);
    List<Libro> buscarPorPrecioMayorA(BigDecimal precio);
    Libro actualizarLibro(Integer id, Libro libro);
    void eliminarLibro(Integer id);
    List<Object[]> promedioPrecioPorAutor();
    List<Object[]> librosConAutorDesdeAnio(Integer anio);
}
