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

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String iniciarSesion() {
        try {
            String hash = hashSHA256(contrasenna);
            user = ufl.iniciarSesion(nombreusuario, hash);

            if (user != null) {
                FacesContext contexto = FacesContext.getCurrentInstance();
                HttpSession sesion = (HttpSession) contexto.getExternalContext().getSession(true);
                this.listaPermisos = pfl.permisosByUser(user);
                sesion.setAttribute("usuario", user);

                // Redirigir según rol
                for (RolesUsuario ru : user.getRolesUsuarioList()) {
                    int rolId = ru.getRolId().getRolId();
                    if (rolId == 1) return "views/inicio.xhtml?faces-redirect=true";
                    if (rolId == 3) return "views/iniciousuario.xhtml?faces-redirect=true";
                }

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario sin permisos asignados", null);
                contexto.addMessage(null, fm);
                return null;
            } else {
                FacesContext contexto = FacesContext.getCurrentInstance();
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña inválidos", null);
                contexto.addMessage(null, fm);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext contexto = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión: " + e.getMessage(), null);
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
        return "login?faces-redirect=true";
    }

    public void invalidarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
