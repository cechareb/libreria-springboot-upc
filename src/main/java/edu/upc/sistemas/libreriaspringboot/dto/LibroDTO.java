package edu.upc.sistemas.libreriaspringboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Objeto de transferencia de datos para la entidad Libro")
public class LibroDTO {

    @Schema(description = "ID del libro", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Título del libro", example = "Cien años de soledad", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @Schema(description = "ISBN del libro", example = "978-0307474728")
    private String isbn;

    @Schema(description = "Año de publicación", example = "1967")
    private Integer anioPublicacion;

    @Schema(description = "Precio de venta", example = "45.50")
    private BigDecimal precio;

    @Schema(description = "ID del autor al que pertenece el libro", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer autorId;

    @Schema(description = "Nombre del autor (solo lectura)", example = "Gabriel García Márquez", accessMode = Schema.AccessMode.READ_ONLY)
    private String nombreAutor;

    // constructores, getters y setters...
    public LibroDTO() {}
    public LibroDTO(Integer id, String titulo, String isbn, Integer anioPublicacion,
                    BigDecimal precio, Integer autorId, String nombreAutor) {
        this.id = id; this.titulo = titulo; this.isbn = isbn;
        this.anioPublicacion = anioPublicacion; this.precio = precio;
        this.autorId = autorId; this.nombreAutor = nombreAutor;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getAutorId() { return autorId; }
    public void setAutorId(Integer autorId) { this.autorId = autorId; }
    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }
}
