package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.dto.CategoriaDTO;
import com.wagnerandrade.cursomc.api.services.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value="Retorna todas as categorias")
    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @ApiOperation(value="Busca por id")
    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Insere categoria")
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = this.service.fromDTO(categoriaDTO);
        categoria = this.service.insert(categoria);

        return ResponseEntity.created(getUri(categoria)).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value="Atualiza categoria")
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
    @ApiOperation(value="Deleta categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
            @ApiResponse(code = 404, message = "Código inexistente") })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private URI getUri(Categoria categoria) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();
    }

    @ApiOperation(value="Retorna todas as categorias com paginação")
    @GetMapping(value = "/page")
    public ResponseEntity getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        return ResponseEntity.ok().body(this.service.findPage(page, linesPerPage, direction, orderBy));
    }
}
