package com.mycompany.learnmate.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entidad que representa la relación entre Roles y Usuarios.
 */
@Entity
@Table(name = "roles_usuario")
public class RolesUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_rol_id")
    private Integer usuarioRolId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    private Roles rolId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuarios usuarioId; // CORREGIDO: este es el atributo que debe usar mappedBy

    // Constructores
    public RolesUsuario() {}

    public RolesUsuario(Integer usuarioRolId) {
        this.usuarioRolId = usuarioRolId;
    }

    // Getters y Setters
    public Integer getUsuarioRolId() {
        return usuarioRolId;
    }

    public void setUsuarioRolId(Integer usuarioRolId) {
        this.usuarioRolId = usuarioRolId;
    }

    public Roles getRolId() {
        return rolId;
    }

    public void setRolId(Roles rolId) {
        this.rolId = rolId;
    }

    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        return usuarioRolId != null ? usuarioRolId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RolesUsuario)) return false;
        RolesUsuario other = (RolesUsuario) obj;
        return usuarioRolId != null && usuarioRolId.equals(other.usuarioRolId);
    }

    @Override
    public String toString() {
        return "RolesUsuario[ usuarioRolId=" + usuarioRolId + " ]";
    }
}
