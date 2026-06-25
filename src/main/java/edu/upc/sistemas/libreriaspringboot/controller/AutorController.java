package edu.upc.sistemas.libreriaspringboot.controller;

import edu.upc.sistemas.libreriaspringboot.dto.AutorDTO;
import edu.upc.sistemas.libreriaspringboot.dto.LibroResumenDTO;
import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /api/autores
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        List<Autor> autores = autorService.listarTodos();
        List<AutorDTO> dtos = new ArrayList<>();

        for (Autor a : autores) {
            dtos.add(convertirADTO(a));
        }

        return ResponseEntity.ok(dtos);
    }

    // GET /api/autores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obtenerPorId(@PathVariable Integer id) {
        Autor autor = autorService.obtenerAutorPorId(id);
        return ResponseEntity.ok(convertirADTO(autor));
    }

    // GET /api/autores/{id}/libros
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroResumenDTO>> obtenerLibrosDeAutor(@PathVariable Integer id) {
        Autor autor = autorService.obtenerAutorConLibros(id);
        List<LibroResumenDTO> librosDTO = new ArrayList<>();

        for (Libro l : autor.getLibros()) {
            librosDTO.add(new LibroResumenDTO(l.getId(), l.getTitulo(), l.getIsbn()));
        }

        return ResponseEntity.ok(librosDTO);
    }

    // GET /api/autores/buscar?nombre=garcia
    @GetMapping("/buscar")
    public ResponseEntity<List<AutorDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<Autor> autores = autorService.buscarPorNombre(nombre);
        List<AutorDTO> dtos = new ArrayList<>();

        for (Autor a : autores) {
            dtos.add(convertirADTO(a));
        }

        return ResponseEntity.ok(dtos);
    }

    // POST /api/autores
    @PostMapping
    public ResponseEntity<AutorDTO> crear(@RequestBody AutorDTO autorDTO) {
        Autor nuevo = new Autor();
        nuevo.setNombre(autorDTO.getNombre());
        nuevo.setNacionalidad(autorDTO.getNacionalidad());
        nuevo.setFechaNacimiento(autorDTO.getFechaNacimiento());

        Autor creado = autorService.crearAutor(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(creado));
    }

    // PUT /api/autores/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> actualizar(@PathVariable Integer id, @RequestBody AutorDTO autorDTO) {
        Autor datos = new Autor();
        datos.setNombre(autorDTO.getNombre());
        datos.setNacionalidad(autorDTO.getNacionalidad());
        datos.setFechaNacimiento(autorDTO.getFechaNacimiento());

        Autor actualizado = autorService.actualizarAutor(id, datos);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    // DELETE /api/autores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        autorService.eliminarAutor(id);
        return ResponseEntity.noContent().build();
    }

    // Método helper de conversión
    private AutorDTO convertirADTO(Autor autor) {
        AutorDTO dto = new AutorDTO(
                autor.getId(),
                autor.getNombre(),
                autor.getNacionalidad(),
                autor.getFechaNacimiento()
        );
        return dto;
    }
}