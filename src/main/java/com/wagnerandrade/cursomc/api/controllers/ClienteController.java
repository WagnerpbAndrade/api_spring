package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.model.dto.ClienteDTO;
import com.wagnerandrade.cursomc.api.model.dto.ClienteNewDTO;
import com.wagnerandrade.cursomc.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = this.service.fromDTO(clienteNewDTO);
        cliente = this.service.insert(cliente);

        return ResponseEntity.created(getUri(cliente)).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = this.service.fromDTO(clienteDTO);
        cliente.setId(id);

        cliente = this.service.update(cliente);

        return cliente != null ?
                ResponseEntity.ok(cliente) :
                ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    private URI getUri(Cliente cliente) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        return ResponseEntity.ok().body(this.service.findPage(page, linesPerPage, direction, orderBy));
    }

}
