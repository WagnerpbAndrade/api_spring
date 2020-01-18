package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.AuthorizationException;
import com.wagnerandrade.cursomc.api.cotrollers.exception.DataIntegrityException;
import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Cidade;
import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.model.Endereco;
import com.wagnerandrade.cursomc.api.model.dto.ClienteDTO;
import com.wagnerandrade.cursomc.api.model.dto.ClienteNewDTO;
import com.wagnerandrade.cursomc.api.model.enums.Perfil;
import com.wagnerandrade.cursomc.api.model.enums.TipoCliente;
import com.wagnerandrade.cursomc.api.repositories.ClienteRepository;
import com.wagnerandrade.cursomc.api.repositories.EnderecoRepository;
import com.wagnerandrade.cursomc.api.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
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

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Cliente getById(Long id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName()
        ));
    }

    public Cliente getByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = this.repository.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName()
            );
        }

        return obj;
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
        try {
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
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente categoria) {
        newObj.setNome(categoria.getNome());
        newObj.setEmail(categoria.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = this.imageService.getJpgImageFromFile(multipartFile);
        jpgImage = this.imageService.cropSquare(jpgImage);
        jpgImage = this.imageService.resize(jpgImage, size);
        String fileName = prefix + user.getId() + ".jpg";

        return this.s3Service.uploadFile(this.imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
