package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.DataIntegrityException;
import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.model.ClienteDTO;
import com.wagnerandrade.cursomc.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public List<ClienteDTO> getAll() {
        return this.repository.findAll().stream().map(ClienteDTO::create).collect(Collectors.toList());
    }

    public Cliente update(Cliente categoria) {
        Cliente newObj = getById(categoria.getId());
        updateData(newObj, categoria);
        return this.repository.save(newObj);
    }

    public void delete(Long id) {
        try{
            this.repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pages = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.repository.findAll(pages).map(ClienteDTO::create);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente categoria) {
        newObj.setNome(categoria.getNome());
        newObj.setEmail(categoria.getEmail());
    }
}
