package edu.upc.sistemas.libreriaspringboot.controller;

import edu.upc.sistemas.libreriaspringboot.dto.LibroDTO;
import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // GET /api/libros
    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarTodos() {
        List<Libro> libros = libroService.listarTodos();
        List<LibroDTO> dtos = new ArrayList<>();

        for (Libro l : libros) {
            dtos.add(convertirADTO(l));
        }

        return ResponseEntity.ok(dtos);
    }

    // GET /api/libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable Integer id) {
        Libro libro = libroService.obtenerLibroPorId(id);
        return ResponseEntity.ok(convertirADTO(libro));
    }

    // GET /api/libros/buscar?titulo=soledad
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        List<LibroDTO> dtos = new ArrayList<>();

        for (Libro l : libros) {
            dtos.add(convertirADTO(l));
        }

        return ResponseEntity.ok(dtos);
    }

    // POST /api/libros
    @PostMapping
    public ResponseEntity<LibroDTO> crear(@RequestBody LibroDTO libroDTO) {
        Autor autor = new Autor();
        autor.setId(libroDTO.getAutorId());

        Libro nuevo = new Libro();
        nuevo.setTitulo(libroDTO.getTitulo());
        nuevo.setIsbn(libroDTO.getIsbn());
        nuevo.setAnioPublicacion(libroDTO.getAnioPublicacion());
        nuevo.setPrecio(libroDTO.getPrecio());
        nuevo.setAutor(autor);

        Libro creado = libroService.crearLibro(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(creado));
    }

    // PUT /api/libros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(@PathVariable Integer id, @RequestBody LibroDTO libroDTO) {
        Autor autor = new Autor();
        autor.setId(libroDTO.getAutorId());

        Libro datos = new Libro();
        datos.setTitulo(libroDTO.getTitulo());
        datos.setIsbn(libroDTO.getIsbn());
        datos.setAnioPublicacion(libroDTO.getAnioPublicacion());
        datos.setPrecio(libroDTO.getPrecio());
        datos.setAutor(autor);

        Libro actualizado = libroService.actualizarLibro(id, datos);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    // DELETE /api/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }

    // Método helper de conversión
    private LibroDTO convertirADTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getAnioPublicacion(),
                libro.getPrecio(),
                libro.getAutor() != null ? libro.getAutor().getId() : null,
                libro.getAutor() != null ? libro.getAutor().getNombre() : null
        );
    }
}
