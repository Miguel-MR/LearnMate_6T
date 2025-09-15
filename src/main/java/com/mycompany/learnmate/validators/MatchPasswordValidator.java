package com.mycompany.learnmate.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("custom.matchPasswordValidator")
public class MatchPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        // 1. Obtener el ID del componente original del atributo (e.g., "contrasenna")
        String passwordId = (String) component.getAttributes().get("passwordId");
        if (passwordId == null) {
            throw new IllegalArgumentException("Error: El atributo 'passwordId' es requerido en el XHTML.");
        }

        // Asumimos que el formulario se llama 'registroForm' para el ID absoluto
        String absolutePasswordId = "registroForm:" + passwordId;
        
        // Buscar el componente original desde la raíz (solución probada y exitosa)
        UIInput passwordComponent = (UIInput) context.getViewRoot().findComponent(absolutePasswordId);

        if (passwordComponent == null) { 
            // Si no se encuentra el componente, se ignora la validación de coincidencia
            // Esto es seguro ya que hay otras validaciones (required, etc.)
            return; 
        } 

        // 2. OBTENER EL VALOR DEL BACKING BEAN (getValue()), la clave del éxito en AJAX.
        Object beanOriginalValue = passwordComponent.getValue(); 

        // 3. Sanitizar y obtener los valores para comparar
        String originalPassword = (beanOriginalValue == null) 
                                ? "" 
                                : beanOriginalValue.toString().trim();
                                
        String confirmPassword = value != null ? value.toString().trim() : "";

        
        // 4. Realizar la comparación.
        if (originalPassword.isEmpty() || confirmPassword.isEmpty()) {
             // Si alguno está vacío, se permite que la validación 'required' del XHTML muestre el error.
             return;
        }
        
        if (!originalPassword.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Las contraseñas no coinciden.",
                    "La contraseña y la confirmación deben ser idénticas."
            );
            throw new ValidatorException(msg);
        }
    }
}