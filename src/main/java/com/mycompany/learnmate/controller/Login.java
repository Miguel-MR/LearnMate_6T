/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Usuario;
import com.mycompany.learnmate.services.UsuarioFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author castr
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String usuario;
    private String contrasenna;
    private Usuario user = new Usuario();
    @EJB
    UsuarioFacadeLocal ufl;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String iniciarSesion() {
        user = ufl.iniciarSesion(usuario, contrasenna);
        if (user != null && user.getNombreUsuario() != null && user.getContrasena() != null) {
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession sesion = (HttpSession) contexto.getExternalContext().getSession(true);
            sesion.setAttribute("usuario", user);
            //Redireccionar a la pagina de inicio
            return "inicio?faces-redirect=true";
        } else {
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña invalidos","MSG_ERROR");
            contexto.addMessage(null, fm);
            return null;
        }
    }

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }

}
