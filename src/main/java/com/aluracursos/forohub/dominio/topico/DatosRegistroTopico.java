package com.aluracursos.forohub.dominio.topico;

import com.aluracursos.forohub.dominio.curso.Curso;
import lombok.NonNull;

import com.aluracursos.forohub.dominio.usuario.Usuario;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotBlank
        String status,

        @NonNull
        Usuario autor,

        @NonNull
        Curso curso
) {

}
