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

            // 🔑 Recuperar el parámetro personaId desde la URL
            String idParam = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequestParameterMap()
                    .get("personaId");

            if (idParam != null && !idParam.trim().isEmpty()) {
                personaId = Integer.parseInt(idParam);
                this.con = cfl.find(personaId);

                if (this.con != null) {
                    // Cargar el usuario asociado
                    List<Usuarios> usuariosAsociados = usuariosFacade.findByPersonaId(this.con);
                    if (usuariosAsociados != null && !usuariosAsociados.isEmpty()) {
                        this.nuevoUsuario = usuariosAsociados.get(0);
                        this.nuevoUsuario.setContrasenna(null); // Limpio para no mostrar el hash
                        this.confirmarContrasenna = null;

                        // Cargar rol
                        List<RolesUsuario> rolesAsociados = rolesUsuarioFacade.findByUsuario(this.nuevoUsuario);
                        if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                            this.rolSeleccionado = rolesAsociados.get(0).getRolId();
                        }
                    }
                }
            } else {
                // Si no hay parámetro, inicializo vacío
                con = new Personas();
                nuevoUsuario = new Usuarios();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearPersona() {
        // Asumiendo que las validaciones (contraseña, no nulos) ya se manejan.

        // Cifrado de contraseña
        String contrasennaHashed = hashPassword(nuevoUsuario.getContrasenna());
        if (contrasennaHashed == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fallo al cifrar la contraseña."));
            return;
        }
        nuevoUsuario.setContrasenna(contrasennaHashed);

        try {

            // 1. Crear Persona (Transacción principal A)
            cfl.create(con);
            // 'con' ahora tiene persona_id

            // 2. Crear Usuario (FORZANDO NUEVA TRANSACCIÓN B)
            nuevoUsuario.setPersona(con); // Establecer la FK de Usuarios a Personas
            nuevoUsuario.setEstadoId(estadoFacade.find(1));

            // 🔑 USO CLAVE: Llama a createAndFlush. La Transacción B se confirma aquí.
            Usuarios usuarioManaged = usuariosFacade.createAndFlush(nuevoUsuario);
            // 'usuarioManaged' es el objeto seguro con el 'usuario_id' generado.

            // 3. Cierre de la Relación Bidireccional (Vuelve a Transacción A)
            con.setUsuario(usuarioManaged); // Establecer la referencia inversa de Personas a Usuarios

            // 🔑 Actualiza Personas con la referencia al Usuario ya persistido.
            cfl.edit(con);

            // 4. Crear RolUsuario
            RolesUsuario ru = new RolesUsuario();
            ru.setUsuarioId(usuarioManaged); // Usar el objeto con ID
            ru.setRolId(rolSeleccionado);
            rolesUsuarioFacade.create(ru);

            // Mensaje de éxito y limpieza
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Persona y usuario creados correctamente."));

            limpiarCampos(); // Llamar a un método para resetear los campos de la vista (que deberías implementar)

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fallo al crear la persona/usuario: " + e.getMessage()));
        }
    }

    // Método auxiliar para limpiar campos (debe ser implementado)
    private void limpiarCampos() {
        con = new Personas();
        nuevoUsuario = new Usuarios();
        rolSeleccionado = null;
        confirmarContrasenna = null;
    }

    // El método 'actualizarPersona()' y el resto del código (prepararEdicion, hashPassword, listarPersonas, getters/setters)
    // permanecen CORRECTOS y no necesitan más modificaciones.
    // 🏆 Lógica para preservar la contraseña si el campo está vacío
    public String actualizarPersona() {
        // ... (Tu código para actualizar la persona/usuario) ...
        // Este código ya se veía correcto en la gestión de actualización
        // ...
        String contrasennaOriginalHash = null;

        try {
            // 1. OBTENER EL USUARIO ORIGINAL para ver si hay que preservar la contraseña
            if (nuevoUsuario != null && nuevoUsuario.getUsuarioId() != null) {
                Usuarios usuarioDB = usuariosFacade.find(nuevoUsuario.getUsuarioId());
                if (usuarioDB != null) {
                    contrasennaOriginalHash = usuarioDB.getContrasenna();
                }
            }

            // 2. Manejo y validación de la contraseña
            String nuevaContrasenna = nuevoUsuario.getContrasenna();

            if (nuevaContrasenna != null && !nuevaContrasenna.trim().isEmpty()) {
                if (!nuevaContrasenna.equals(confirmarContrasenna)) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden", null));
                    return null;
                }
                nuevoUsuario.setContrasenna(hashPassword(nuevaContrasenna));
            } else {
                nuevoUsuario.setContrasenna(contrasennaOriginalHash);
            }

            // 3. Actualizar Persona y Usuario
            cfl.edit(con);
            if (nuevoUsuario != null) {
                usuariosFacade.edit(nuevoUsuario);
            }

            // 4. Actualizar rol
            if (rolSeleccionado != null) {
                List<RolesUsuario> rolesAsociados = rolesUsuarioFacade.findByUsuario(nuevoUsuario);
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

            return "/views/usuarios/list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar: " + e.getMessage(), null));
            return null;
        }
    }

    // ... (Otros métodos y Getters/Setters se mantienen inalterados) ...
    public void eliminarPersona(Personas persona) {
        // Lógica de eliminación...
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
        // ... (Tu código para preparar la edición) ...
        Integer id = persona.getPersonaId();

        if (id == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de Persona no disponible."));
            return null;
        }

        try {
            this.con = cfl.find(id);

            if (this.con == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Persona no encontrada en la base de datos."));
                return null;
            }

            List<Usuarios> usuariosAsociados = usuariosFacade.findByPersonaId(this.con);

            if (usuariosAsociados != null && !usuariosAsociados.isEmpty()) {
                this.nuevoUsuario = usuariosAsociados.get(0);
                this.nuevoUsuario.setContrasenna(null);
                this.confirmarContrasenna = null;

                List<RolesUsuario> rolesAsociados = rolesUsuarioFacade.findByUsuario(this.nuevoUsuario);

                if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                    this.rolSeleccionado = rolesAsociados.get(0).getRolId();
                } else {
                    this.rolSeleccionado = null;
                }
            } else {
                this.nuevoUsuario = new Usuarios();
                this.rolSeleccionado = null;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No se encontró usuario asociado a esta persona."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fallo al cargar la entidad: " + e.getMessage()));
            return null;
        }

        return "/views/usuarios/editar.xhtml?faces-redirect=true&amp;personaId=" + id;
    }

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
