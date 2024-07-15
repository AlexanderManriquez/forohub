package com.aluracursos.forohub.dominio.usuario;

public record AuthData(String nombre, String clave) {
    public static String nombre(AuthData authData) {
        return authData.nombre;
    }

    public static String clave(AuthData authData) {
        return authData.clave;
    }
}
