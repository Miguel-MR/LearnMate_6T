package com.mycompany.learnmate.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("custom.emailValidator") // ID Único
public class EmailValidator implements Validator {

    // Patrón de email robusto (puede ser el mismo que usabas en el XHTML)
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        
        if (value == null) return;
        
        String email = value.toString();
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Correo inválido.", 
                "El formato del correo electrónico no es válido (ej: usuario@dominio.com)."
            );
            throw new ValidatorException(msg);
        }
    }
}