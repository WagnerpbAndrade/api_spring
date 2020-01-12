package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.dto.CategoriaDTO;
import com.wagnerandrade.cursomc.api.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = this.service.fromDTO(categoriaDTO);
        categoria = this.service.insert(categoria);

        return ResponseEntity.created(getUri(categoria)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = this.service.fromDTO(categoriaDTO);
        categoria.setId(id);

        categoria = this.service.update(categoria);

        return categoria != null ?
                ResponseEntity.ok(categoria) :
                ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private URI getUri(Categoria categoria) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();
    }

    @GetMapping(value = "/page")
    public ResponseEntity getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        return ResponseEntity.ok().body(this.service.findPage(page, linesPerPage, direction, orderBy));
    }
}
