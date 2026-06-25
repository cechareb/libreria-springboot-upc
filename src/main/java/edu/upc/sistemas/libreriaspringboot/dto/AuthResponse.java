package edu.upc.sistemas.libreriaspringboot.dto;

import edu.upc.sistemas.libreriaspringboot.entity.Usuario;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String usuario;
    private String rol;

    public AuthResponse(String token, String usuario, String rol) {
        this.token = token;
        this.usuario = usuario;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
