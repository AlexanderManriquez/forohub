package com.aluracursos.forohub.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aluracursos.forohub.dominio.curso.DatosRespuestaCurso;
import com.aluracursos.forohub.dominio.topico.DatosActualizarTopico;
import com.aluracursos.forohub.dominio.topico.DatosListadoTopico;
import com.aluracursos.forohub.dominio.topico.DatosRegistroTopico;
import com.aluracursos.forohub.dominio.topico.DatosRespuestaTopico;
import com.aluracursos.forohub.dominio.topico.RepositoryTopico;
import com.aluracursos.forohub.dominio.topico.Topico;
import com.aluracursos.forohub.dominio.usuario.DatosRespuestaUsuario;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/topico")
public class ControllerTopico {
    private final RepositoryTopico topicoRepository;

    public ControllerTopico (RepositoryTopico repositoryTopico) {
        this.topicoRepository = repositoryTopico;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> nuevoTopico(
            @RequestBody @Validated DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico=new DatosRespuestaTopico(topico.getId(),topico.getTitulo(),
                topico.getMensaje(),topico.getFechaCreacion(),new DatosRespuestaUsuario(topico.getAutor().getNombre(),
                topico.getAutor().getCorreo()),new DatosRespuestaCurso(topico.getCurso().getNombre(),topico.getCurso().getCategoria()));
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopico(@PageableDefault(size = 5) Pageable pag){
        return ResponseEntity.ok(topicoRepository.findAll(pag).map(DatosListadoTopico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Validated DatosActualizarTopico datosActualizarTopico){
        Topico topico=topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),new DatosRespuestaUsuario(topico.getAutor().getNombre(),topico.getAutor().getCorreo()),
                new DatosRespuestaCurso(topico.getCurso().getNombre(),topico.getCurso().getCategoria())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.delete(topicoOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoId(@PathVariable Long id){
        Topico topico=topicoRepository.getReferenceById(id);
        var datosTopico=new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getFechaCreacion(),new DatosRespuestaUsuario(topico.getAutor().getNombre(),topico.getAutor().getCorreo()),
                new DatosRespuestaCurso(topico.getCurso().getNombre(),topico.getCurso().getCategoria()));
        return ResponseEntity.ok(datosTopico);
    }
}
