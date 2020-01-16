package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {

        Cliente cliente = this.clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(this.encoder.encode(newPass));

        this.clienteRepository.save(cliente);
        this.emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }

        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) { // gera um dígito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) { // gera letra maiúscula
            return (char) (rand.nextInt(26) + 65);
        } else { //  gera letra minúscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
