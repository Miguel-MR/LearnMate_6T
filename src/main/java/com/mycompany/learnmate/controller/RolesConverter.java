package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Roles;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "rolesConverter", forClass = Roles.class)
public class RolesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.trim().isEmpty()) {
            Roles r = new Roles();
            r.setRolId(Integer.valueOf(value));
            return r;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Roles) {
            return String.valueOf(((Roles) value).getRolId());
        }
        return "";
    }
}