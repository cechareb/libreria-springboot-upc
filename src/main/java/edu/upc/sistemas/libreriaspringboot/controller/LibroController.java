package edu.upc.sistemas.libreriaspringboot.controller;

import edu.upc.sistemas.libreriaspringboot.dto.LibroDTO;
import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Operaciones CRUD y consultas sobre la entidad Libro")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Operation(summary = "Listar todos los libros", description = "Retorna el catálogo completo de libros con información del autor")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = LibroDTO.class)))
    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarTodos() {
        List<Libro> libros = libroService.listarTodos();
        List<LibroDTO> dtos = new ArrayList<>();

        for (Libro l : libros) {
            dtos.add(convertirADTO(l));
        }

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Obtener libro por ID", description = "Consulta un libro específico incluyendo los datos de su autor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "400", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(
            @Parameter(description = "ID del libro", example = "10") @PathVariable Integer id) {
        Libro libro = libroService.obtenerLibroPorId(id);
        return ResponseEntity.ok(convertirADTO(libro));
    }

    @Operation(summary = "Buscar libros por título", description = "Búsqueda parcial e insensible a mayúsculas sobre el título del libro")
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDTO>> buscarPorTitulo(
            @Parameter(description = "Fragmento del título a buscar", example = "soledad") @RequestParam String titulo) {
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        List<LibroDTO> dtos = new ArrayList<>();

        for (Libro l : libros) {
            dtos.add(convertirADTO(l));
        }

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Crear nuevo libro", description = "Registra un libro vinculado a un autor existente. Valida que el autor exista en base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Autor no encontrado o datos inválidos")
    })
    @PostMapping
    public ResponseEntity<LibroDTO> crear(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro incluyendo el ID del autor",
                    content = @Content(schema = @Schema(implementation = LibroDTO.class))
            ) LibroDTO libroDTO) {
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

    @Operation(summary = "Actualizar libro", description = "Modifica los datos de un libro existente, permitiendo cambiar de autor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado"),
            @ApiResponse(responseCode = "400", description = "Libro o autor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(
            @Parameter(description = "ID del libro") @PathVariable Integer id,
            @RequestBody LibroDTO libroDTO) {
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

    @Operation(summary = "Eliminar libro", description = "Elimina un libro del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "400", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del libro a eliminar") @PathVariable Integer id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }

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
