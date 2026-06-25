package edu.upc.sistemas.libreriaspringboot.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AutorDTO {
    private Integer id;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private List<LibroResumenDTO> libros = new ArrayList<>();

    // Constructores
    public AutorDTO() {
    }

    public AutorDTO(Integer id, String nombre, String nacionalidad, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<LibroResumenDTO> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroResumenDTO> libros) {
        this.libros = libros;
    }
}
