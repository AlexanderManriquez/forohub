package com.aluracursos.forohub.dominio.topico;

import java.util.Date;

import com.aluracursos.forohub.dominio.curso.DatosRespuestaCurso;
import com.aluracursos.forohub.dominio.usuario.DatosRespuestaUsuario;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, Date fechaCreacion, DatosRespuestaUsuario autor,
                                   DatosRespuestaCurso curso) {

}
