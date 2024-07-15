package com.aluracursos.forohub.dominio.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositoryUsuario  extends JpaRepository<Usuario,Long>{
    UserDetails findByNombre(String nombre);
}
