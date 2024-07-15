package com.aluracursos.forohub.dominio.topico;

import jakarta.annotation.Nonnull;

public record DatosActualizarTopico(@Nonnull Long id, String titulo, String mensaje,
                                    String status) {

}
