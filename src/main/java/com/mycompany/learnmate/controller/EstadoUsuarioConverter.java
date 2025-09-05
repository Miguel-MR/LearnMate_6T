package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.EstadoUsuario;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "estadoUsuarioConverter", forClass = EstadoUsuario.class)
public class EstadoUsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.trim().isEmpty()) {
            EstadoUsuario estado = new EstadoUsuario();
            estado.setEstadoId(Integer.valueOf(value));
            return estado;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof EstadoUsuario) {
            return String.valueOf(((EstadoUsuario) value).getEstadoId());
        }
        return "";
    }
}