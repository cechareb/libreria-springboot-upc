package edu.upc.sistemas.libreriaspringboot.service;

import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.repository.AutorRepository;
import edu.upc.sistemas.libreriaspringboot.repository.LibroRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class LibroServiceImpl implements LibroService {
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public Libro crearLibro(Libro libro) {
        // Validar que el autor exista
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new RuntimeException("El libro debe tener un autor válido");
        }
        Autor autor = autorRepository.findById(libro.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        libro.setAutor(autor);
        return libroRepository.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro obtenerLibroPorId(Integer id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarPorAutor(Integer autorId) {
        return libroRepository.findByAutorId(autorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarPorPrecioMayorA(BigDecimal precio) {
        return libroRepository.findByPrecioGreaterThan(precio);
    }

    @Override
    public Libro actualizarLibro(Integer id, Libro libroActualizado) {
        Libro libro = obtenerLibroPorId(id);
        libro.setTitulo(libroActualizado.getTitulo());
        libro.setIsbn(libroActualizado.getIsbn());
        libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
        libro.setPrecio(libroActualizado.getPrecio());

        // Si cambia el autor, validar
        if (libroActualizado.getAutor() != null && libroActualizado.getAutor().getId() != null) {
            Autor nuevoAutor = autorRepository.findById(libroActualizado.getAutor().getId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            libro.setAutor(nuevoAutor);
        }

        return libroRepository.save(libro);
    }

    @Override
    public void eliminarLibro(Integer id) {
        Libro libro = obtenerLibroPorId(id);
        libroRepository.delete(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> promedioPrecioPorAutor() {
        return libroRepository.promedioPrecioPorAutor();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> librosConAutorDesdeAnio(Integer anio) {
        return libroRepository.findLibrosConAutorDesdeAnio(anio);
    }
}
