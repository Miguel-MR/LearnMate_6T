package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRolId", query = "SELECT r FROM Roles r WHERE r.rolId = :rolId"),
    @NamedQuery(name = "Roles.findByNombreRol", query = "SELECT r FROM Roles r WHERE r.nombreRol = :nombreRol")
})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rol_id")
    private Integer rolId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_rol")
    private String nombreRol;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private Collection<RolPermisos> rolPermisosCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolId")
    private Collection<RolesUsuario> rolesUsuarioCollection;

    public Roles() {
    }

    public Roles(Integer rolId) {
        this.rolId = rolId;
    }

    public Roles(Integer rolId, String nombreRol) {
        this.rolId = rolId;
        this.nombreRol = nombreRol;
    }

    // Getters y Setters
    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @XmlTransient
    public Collection<RolPermisos> getRolPermisosCollection() {
        return rolPermisosCollection;
    }

    public void setRolPermisosCollection(Collection<RolPermisos> rolPermisosCollection) {
        this.rolPermisosCollection = rolPermisosCollection;
    }

    @XmlTransient
    public Collection<RolesUsuario> getRolesUsuarioCollection() {
        return rolesUsuarioCollection;
    }

    public void setRolesUsuarioCollection(Collection<RolesUsuario> rolesUsuarioCollection) {
        this.rolesUsuarioCollection = rolesUsuarioCollection;
    }

    // equals y hashCode usando solo PK
    @Override
    public int hashCode() {
        return rolId != null ? rolId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Roles)) return false;
        Roles other = (Roles) object;
        return rolId != null && rolId.equals(other.rolId);
    }

    @Override
    public String toString() {
        return "Roles[ rolId=" + rolId + ", nombreRol=" + nombreRol + " ]";
    }
}
