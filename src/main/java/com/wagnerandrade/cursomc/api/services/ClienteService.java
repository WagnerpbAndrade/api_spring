package com.wagnerandrade.cursomc.api.services;

import com.sun.org.apache.xpath.internal.operations.Mult;
import com.wagnerandrade.cursomc.api.cotrollers.exception.DataIntegrityException;
import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Cidade;
import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.model.Endereco;
import com.wagnerandrade.cursomc.api.model.dto.ClienteDTO;
import com.wagnerandrade.cursomc.api.model.dto.ClienteNewDTO;
import com.wagnerandrade.cursomc.api.model.enums.TipoCliente;
import com.wagnerandrade.cursomc.api.repositories.ClienteRepository;
import com.wagnerandrade.cursomc.api.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private S3Service s3Service;

    public Cliente getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public Cliente getByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public List<ClienteDTO> getAll() {
        return this.repository.findAll().stream().map(ClienteDTO::create).collect(Collectors.toList());
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = this.repository.save(obj);
        this.enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
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
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
        }
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pages = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.repository.findAll(pages).map(ClienteDTO::create);
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), encoder.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2()!=null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3()!=null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente categoria) {
        newObj.setNome(categoria.getNome());
        newObj.setEmail(categoria.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        return this.s3Service.uploadFile(multipartFile);
    }
}
