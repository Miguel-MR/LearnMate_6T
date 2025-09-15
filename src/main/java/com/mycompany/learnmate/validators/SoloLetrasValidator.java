package com.mycompany.learnmate.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.soloLetrasValidator") 
public class SoloLetrasValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        
        // Si el valor es nulo, dejamos que la validación 'required' lo maneje.
        if (value == null) {
            return; 
        }
        
        String texto = value.toString().trim();
        
        // -------------------------------------------------------------------
        // ** SOLUCIÓN CLAVE: Si el campo NO es obligatorio y está vacío, salimos.
        // -------------------------------------------------------------------
        if (texto.isEmpty()) {
            return; 
        }
        
        // Expresión Regular: [a-zA-Z\s]+
        // También puedes incluir tildes (áéíóúÁÉÍÓÚ) y la letra ñ/Ñ si es necesario
        // Ejemplo con tildes y ñ: "[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+"
        if (!texto.matches("[a-zA-Z\\s]+")) {
            
            FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Formato del Nombre/Apellido incorrecto.", 
                "El campo solo debe contener letras y espacios."
            );
            
            throw new ValidatorException(msg);
        }
    }
}