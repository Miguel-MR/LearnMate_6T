package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad Usuarios con relación hacia Personas y RolesUsuario
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsuarioId", query = "SELECT u FROM Usuarios u WHERE u.usuarioId = :usuarioId"),
    @NamedQuery(name = "Usuarios.findByNombreusuario", query = "SELECT u FROM Usuarios u WHERE u.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "Usuarios.findByContrasenna", query = "SELECT u FROM Usuarios u WHERE u.contrasenna = :contrasenna")
})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombreusuario")
    private String nombreusuario;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "contrasenna")
    private String contrasenna;
    
    // ✅ MISSING FIELD: Mapped to the NOT NULL 'correo' column in the database
    @NotNull 
    @Size(min = 1, max = 255) 
    @Column(name = "correo", unique = true) // Added unique constraint as per DB schema
    private String correo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_id", referencedColumnName = "estado_id")
    private EstadoUsuario estadoId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    private Personas persona;

    @OneToMany(mappedBy = "usuarioId", cascade = CascadeType.ALL)
    private List<RolesUsuario> rolesUsuarioList;

    // Constructores
    public Usuarios() {}

    public Usuarios(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    // ✅ CONSTRUCTOR UPDATED to include 'correo'
    public Usuarios(String nombreusuario, String contrasenna, String correo, EstadoUsuario estadoId, Personas persona) {
        this.nombreusuario = nombreusuario;
        this.contrasenna = contrasenna;
        this.correo = correo; // Correo initialization
        this.estadoId = estadoId;
        this.persona = persona;
    }

    // Getters y Setters
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
    
    // ✅ GETTER for correo
    public String getCorreo() {
        return correo;
    }

    // ✅ SETTER for correo
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public EstadoUsuario getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(EstadoUsuario estadoId) {
        this.estadoId = estadoId;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public List<RolesUsuario> getRolesUsuarioList() {
        return rolesUsuarioList;
    }

    public void setRolesUsuarioList(List<RolesUsuario> rolesUsuarioList) {
        this.rolesUsuarioList = rolesUsuarioList;
    }

    // equals y hashCode usando solo PK
    @Override
    public int hashCode() {
        return usuarioId != null ? usuarioId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Usuarios)) return false;
        Usuarios other = (Usuarios) obj;
        return usuarioId != null && usuarioId.equals(other.usuarioId);
    }

    @Override
    public String toString() {
        return "Usuarios[ usuarioId=" + usuarioId + ", nombreusuario=" + nombreusuario + ", correo=" + correo + " ]"; // Added correo to toString
    }
}