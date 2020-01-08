package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.model.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}
