package edu.upc.sistemas.libreriaspringboot.controller;

import edu.upc.sistemas.libreriaspringboot.dto.AutorDTO;
import edu.upc.sistemas.libreriaspringboot.dto.LibroResumenDTO;
import edu.upc.sistemas.libreriaspringboot.entity.Autor;
import edu.upc.sistemas.libreriaspringboot.entity.Libro;
import edu.upc.sistemas.libreriaspringboot.service.AutorService;
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
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "Operaciones CRUD y consultas sobre la entidad Autor")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @Operation(
            summary = "Listar todos los autores",
            description = "Retorna una lista completa de autores registrados en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AutorDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        List<Autor> autores = autorService.listarTodos();
        List<AutorDTO> dtos = new ArrayList<>();

        for (Autor a : autores) {
            dtos.add(convertirADTO(a));
        }

        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Obtener autor por ID",
            description = "Busca un autor específico mediante su identificador único"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Autor no encontrado",
                    content = @Content(schema = @Schema(implementation = java.util.Map.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obtenerPorId(
            @Parameter(description = "ID numérico del autor", example = "1")
            @PathVariable Integer id) {
        Autor autor = autorService.obtenerAutorPorId(id);
        return ResponseEntity.ok(convertirADTO(autor));
    }

    @Operation(
            summary = "Obtener libros de un autor",
            description = "Retorna el listado de libros asociados a un autor, resolviendo la relación OneToMany"
    )
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroResumenDTO>> obtenerLibrosDeAutor(
            @Parameter(description = "ID del autor") @PathVariable Integer id) {
        Autor autor = autorService.obtenerAutorConLibros(id);
        List<LibroResumenDTO> librosDTO = new ArrayList<>();

        for (Libro l : autor.getLibros()) {
            librosDTO.add(new LibroResumenDTO(l.getId(), l.getTitulo(), l.getIsbn()));
        }

        return ResponseEntity.ok(librosDTO);
    }

    @Operation(
            summary = "Buscar autores por nombre",
            description = "Realiza una búsqueda parcial e insensible a mayúsculas/minúsculas sobre el nombre del autor"
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<AutorDTO>> buscarPorNombre(
            @Parameter(description = "Texto a buscar dentro del nombre", example = "garcia")
            @RequestParam String nombre) {
        List<Autor> autores = autorService.buscarPorNombre(nombre);
        List<AutorDTO> dtos = new ArrayList<>();

        for (Autor a : autores) {
            dtos.add(convertirADTO(a));
        }

        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "Crear un nuevo autor",
            description = "Registra un autor en la base de datos con los datos proporcionados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente",
                    content = @Content(schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<AutorDTO> crear(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del autor a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AutorDTO.class))
            ) AutorDTO autorDTO) {
        Autor nuevo = new Autor();
        nuevo.setNombre(autorDTO.getNombre());
        nuevo.setNacionalidad(autorDTO.getNacionalidad());
        nuevo.setFechaNacimiento(autorDTO.getFechaNacimiento());

        Autor creado = autorService.crearAutor(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirADTO(creado));
    }

    @Operation(summary = "Actualizar autor existente", description = "Modifica los datos de un autor identificado por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor actualizado"),
            @ApiResponse(responseCode = "400", description = "Autor no encontrado o datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> actualizar(
            @Parameter(description = "ID del autor a actualizar") @PathVariable Integer id,
            @RequestBody AutorDTO autorDTO) {
        Autor datos = new Autor();
        datos.setNombre(autorDTO.getNombre());
        datos.setNacionalidad(autorDTO.getNacionalidad());
        datos.setFechaNacimiento(autorDTO.getFechaNacimiento());

        Autor actualizado = autorService.actualizarAutor(id, datos);
        return ResponseEntity.ok(convertirADTO(actualizado));
    }

    @Operation(summary = "Eliminar autor", description = "Elimina un autor y sus libros asociados en cascada")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "400", description = "Autor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del autor a eliminar") @PathVariable Integer id) {
        autorService.eliminarAutor(id);
        return ResponseEntity.noContent().build();
    }

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