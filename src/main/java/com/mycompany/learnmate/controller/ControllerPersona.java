package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.*;
import com.mycompany.learnmate.services.*;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;

@Named(value = "controllerPersona")
@ViewScoped
public class ControllerPersona implements Serializable {

    private Personas con = new Personas();

    @EJB
    private PersonasFacadeLocal cfl;

    @EJB
    private UsuariosFacadeLocal usuariosFacade;

    @EJB
    private EstadoUsuarioFacadeLocal estadoFacade;

    @EJB
    private RolesFacadeLocal rolesFacade;

    @EJB
    private RolesUsuarioFacadeLocal rolesUsuarioFacade;

    private List<Roles> listaRoles;
    private List<EstadoUsuario> listaEstados;

    private Usuarios nuevoUsuario = new Usuarios();
    private Roles rolSeleccionado;
    private String confirmarContrasenna;

    private Integer personaId;

    @PostConstruct
    public void init() {
        try {
            listaEstados = estadoFacade.findAll();
            listaRoles = rolesFacade.findAll();

            String idParam = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("personaId");

            if (idParam != null && !idParam.trim().isEmpty()) {
                Integer id = Integer.parseInt(idParam);
                con = cfl.find(id);

                if (con != null) {
                    // 🔹 Cargar usuario asociado
                    List<Usuarios> usuariosAsociados = usuariosFacade.findByPersonaId(con);
                    if (usuariosAsociados != null && !usuariosAsociados.isEmpty()) {
                        nuevoUsuario = usuariosAsociados.get(0);

                        // 🔹 Cargar rol asociado
                        List<RolesUsuario> rolesAsociados = rolesUsuarioFacade.findByUsuarioId(nuevoUsuario);
                        if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                            rolSeleccionado = rolesAsociados.get(0).getRolId();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearPersona() {
        try {
            if (!nuevoUsuario.getContrasenna().equals(confirmarContrasenna)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden", null));
                return;
            }

            String hashedPassword = hashPassword(nuevoUsuario.getContrasenna());
            nuevoUsuario.setContrasenna(hashedPassword);

            // Crear la persona primero
            cfl.create(con);

            // Cambiado a setEstadoUsuario
            nuevoUsuario.setEstadoId(estadoFacade.find(1));
            nuevoUsuario.setPersona(con);

            usuariosFacade.create(nuevoUsuario);

            RolesUsuario ru = new RolesUsuario();
            ru.setUsuarioId(nuevoUsuario);
            ru.setRolId(rolSeleccionado);
            rolesUsuarioFacade.create(ru);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", null));

            con = new Personas();
            nuevoUsuario = new Usuarios();
            confirmarContrasenna = null;
            rolSeleccionado = null;

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar: " + e.getMessage(), null));
        }
    }

    public String actualizarPersona() {
        try {
            // Actualizar persona
            cfl.edit(con);

            // Actualizar usuario
            if (nuevoUsuario != null) {
                // Solo cifrar si la contraseña fue modificada
                if (nuevoUsuario.getContrasenna() != null && !nuevoUsuario.getContrasenna().isEmpty()) {
                    nuevoUsuario.setContrasenna(hashPassword(nuevoUsuario.getContrasenna()));
                }
                usuariosFacade.edit(nuevoUsuario);
            }

            // Actualizar rol
            if (rolSeleccionado != null) {
                List<RolesUsuario> rolesAsociados = rolesUsuarioFacade.findByUsuarioId(nuevoUsuario);
                if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                    RolesUsuario ru = rolesAsociados.get(0);
                    ru.setRolId(rolSeleccionado);
                    rolesUsuarioFacade.edit(ru);
                } else {
                    RolesUsuario ruNuevo = new RolesUsuario();
                    ruNuevo.setUsuarioId(nuevoUsuario);
                    ruNuevo.setRolId(rolSeleccionado);
                    rolesUsuarioFacade.create(ruNuevo);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Persona y usuario actualizados correctamente", null));

            return "/views/personas/index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar: " + e.getMessage(), null));
            return null;
        }
    }

    public void eliminarPersona(Personas persona) {
        try {
            List<Usuarios> usuariosAsociados = usuariosFacade.findByPersonaId(persona);

            for (Usuarios u : usuariosAsociados) {
                List<RolesUsuario> roles = rolesUsuarioFacade.findByUsuarioId(u);
                for (RolesUsuario r : roles) {
                    rolesUsuarioFacade.remove(r);
                }
                usuariosFacade.remove(u);
            }

            cfl.remove(persona);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Persona y usuario eliminados", null));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar: " + e.getMessage(), null));
        }
    }

    private String hashPassword(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(plainText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cifrar la contraseña", e);
        }
    }

    public List<Personas> listarPersonas() {
        try {
            return cfl.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar personas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String prepararEdicion(Personas persona) {
        this.con = persona;
        return "/views/usuarios/index.xhtml";
    }

    // Getters y Setters
    public Personas getCon() {
        return con;
    }

    public void setCon(Personas con) {
        this.con = con;
    }

    public Usuarios getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuarios nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public Roles getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Roles rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public String getConfirmarContrasenna() {
        return confirmarContrasenna;
    }

    public void setConfirmarContrasenna(String confirmarContrasenna) {
        this.confirmarContrasenna = confirmarContrasenna;
    }

    public List<Roles> getListaRoles() {
        return listaRoles;
    }

    public List<EstadoUsuario> getListaEstados() {
        return listaEstados;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
}
