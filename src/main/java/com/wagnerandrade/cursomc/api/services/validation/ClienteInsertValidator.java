package com.wagnerandrade.cursomc.api.services.validation;


import com.wagnerandrade.cursomc.api.model.dto.ClienteNewDTO;
import com.wagnerandrade.cursomc.api.model.enums.TipoCliente;
import com.wagnerandrade.cursomc.api.services.exception.FieldMessage;
import com.wagnerandrade.cursomc.api.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
