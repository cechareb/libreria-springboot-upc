package edu.upc.sistemas.libreriaspringboot.dto;

public class LibroResumenDTO {
    private Integer id;
    private String titulo;
    private String isbn;

    public LibroResumenDTO() {
    }

    public LibroResumenDTO(Integer id, String titulo, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
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
}
