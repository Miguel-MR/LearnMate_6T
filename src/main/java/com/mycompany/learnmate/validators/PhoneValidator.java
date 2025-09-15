package com.mycompany.learnmate.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.phoneValidator") // ID Único
public class PhoneValidator implements Validator {

    private static final int MIN_LENGTH = 7;
    private static final int MAX_LENGTH = 15;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        
        if (value == null) return;
        
        String phone = value.toString().replaceAll("[^0-9]", ""); // Quita cualquier caracter no numérico
        
        // 1. Validar que solo sean dígitos
        if (!value.toString().matches("\\d+")) {
            FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Teléfono inválido.", 
                "El número de teléfono solo debe contener dígitos (0-9)."
            );
            throw new ValidatorException(msg);
        }

        // 2. Validar longitud
        if (phone.length() < MIN_LENGTH || phone.length() > MAX_LENGTH) {
            FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Longitud incorrecta.", 
                "El teléfono debe tener entre " + MIN_LENGTH + " y " + MAX_LENGTH + " dígitos."
            );
            throw new ValidatorException(msg);
        }
    }
}