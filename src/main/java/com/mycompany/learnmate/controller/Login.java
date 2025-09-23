package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Permisos;
import com.mycompany.learnmate.entities.RolesUsuario;
import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.services.PermisosFacadeLocal;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String nombreusuario;
    private String contrasenna;
    private Usuarios user;
    private List<Permisos> listaPermisos;

    RolesUsuario use = new RolesUsuario();

    @EJB
    private UsuariosFacadeLocal ufl;
    @EJB
    private PermisosFacadeLocal pfl;

    public Login() {
        user = new Usuarios();
    }

    public List<Permisos> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permisos> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public String getUsuario() {
        return nombreusuario;
    }

    public void setUsuario(String usuario) {
        this.nombreusuario = usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String iniciarSesion() {
   
        System.out.println("Usuario ingresado: " + nombreusuario);
        System.out.println("Contraseña original: " + contrasenna);
        System.out.println("Hash SHA-256 generado: ");

        user = ufl.iniciarSesion(nombreusuario, contrasenna);

        if (user != null && user.getNombreusuario() != null && user.getContrasenna() != null && user.getIdRol().getRolId() == 1) {
            System.out.println("Inicio de sesión exitoso. Usuario encontrado: " + user.getNombreusuario());
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession sesion = (HttpSession) contexto.getExternalContext().getSession(true);
            this.listaPermisos = pfl.permisosByUser(user);
            sesion.setAttribute("usuario", user);
            return "views/inicio.xhtml?faces-redirect=true";
        } else if (user != null && user.getNombreusuario() != null && user.getContrasenna() != null && user.getIdRol().getRolId() == 3) {
            System.out.println("Inicio de sesión exitoso. Usuario encontrado: " + user.getNombreusuario());
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession sesion = (HttpSession) contexto.getExternalContext().getSession(true);
            this.listaPermisos = pfl.permisosByUser(user);
            sesion.setAttribute("usuario", user);
            return "views/iniciousuario.xhtml?faces-redirect=true";
        } else {
            System.out.println("Inicio de sesión fallido.");
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña inválidos", "MSG_ERROR");
            contexto.addMessage(null, fm);
            return null;
        }
    }

    private String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash SHA-256", e);
        }
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true"; // Cambia "login" si tu página tiene otro nombre
    }

    public void invalidarSesion() {

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

    }
}
