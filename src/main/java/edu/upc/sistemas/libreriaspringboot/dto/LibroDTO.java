package edu.upc.sistemas.libreriaspringboot.dto;

import java.math.BigDecimal;

public class LibroDTO {
    private Integer id;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private BigDecimal precio;
    private Integer autorId;
    private String nombreAutor;

    public LibroDTO() {
    }

    public LibroDTO(Integer id, String titulo, String isbn, Integer anioPublicacion,
                    BigDecimal precio, Integer autorId, String nombreAutor) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
        this.autorId = autorId;
        this.nombreAutor = nombreAutor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }
}
