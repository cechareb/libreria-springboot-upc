package edu.upc.sistemas.libreriaspringboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Objeto de transferencia de datos para la entidad Autor")
public class AutorDTO {

    @Schema(description = "Identificador único del autor", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre completo del autor", example = "Gabriel García Márquez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Nacionalidad del autor", example = "Colombiana")
    private String nacionalidad;

    @Schema(description = "Fecha de nacimiento del autor", example = "1927-03-06")
    private LocalDate fechaNacimiento;

    @Schema(description = "Lista de libros asociados al autor (solo se incluye en consultas detalladas)")
    private List<LibroResumenDTO> libros = new ArrayList<>();

    // constructores, getters y setters...
    public AutorDTO() {
    }

    public AutorDTO(Integer id, String nombre, String nacionalidad, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public List<LibroResumenDTO> getLibros() { return libros; }
    public void setLibros(List<LibroResumenDTO> libros) { this.libros = libros; }
}
