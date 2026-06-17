package edu.upc.sistemas.libreriaspringboot.service;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;

import java.util.List;

public interface AutorService {

    Autor crearAutor(Autor autor);
    Autor obtenerAutorPorId(Integer id);
    Autor obtenerAutorConLibros(Integer id);
    List<Autor> listarTodos();
    List<Autor> buscarPorNombre(String nombre);
    List<Autor> buscarPorNacionalidad(String nacionalidad);
    Autor actualizarAutor(Integer id, Autor autorActualizar);
    void eliminarAutor(Integer id);
    List<Object[]> contarLibrosPorAutor();
}
