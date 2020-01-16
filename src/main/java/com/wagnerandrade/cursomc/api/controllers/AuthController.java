package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.dto.EmailDTO;
import com.wagnerandrade.cursomc.api.security.JwtUtil;
import com.wagnerandrade.cursomc.api.security.UserSS;
import com.wagnerandrade.cursomc.api.services.AuthService;
import com.wagnerandrade.cursomc.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @RequestMapping(value="/refresh_token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
        this.authService.sendNewPassword(objDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
