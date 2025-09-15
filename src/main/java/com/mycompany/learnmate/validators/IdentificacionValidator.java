package com.mycompany.learnmate.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.identificacionValidator") 
public class IdentificacionValidator implements Validator {

    // Define la longitud máxima permitida para la identificación
    private static final int MAX_LENGTH = 15;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        
        if (value == null) {
            return; 
        }
        
        String input = value.toString().trim();
        
        // 1. Manejar cadena vacía (si el campo no es obligatorio)
        if (input.isEmpty()) {
            return;
        }

        // 2. Validar Longitud Máxima
        if (input.length() > MAX_LENGTH) {
             FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Longitud de la identificación incorrecta.", 
                "La identificación no puede exceder los " + MAX_LENGTH + " caracteres."
            );
            throw new ValidatorException(msg);
        }

        // 3. Validar Caracteres (Solo dígitos del 0 al 9)
        // Si necesitas guiones o puntos, usa: "[0-9\\s\\.\\-]+".
        if (!input.matches("[0-9]+")) {
            FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Formato de Identificación incorrecto.", 
                "El campo solo debe contener números."
            );
            throw new ValidatorException(msg);
        }
    }
}