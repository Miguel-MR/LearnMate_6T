/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p"),
    @NamedQuery(name = "Permisos.findByPermisosId", query = "SELECT p FROM Permisos p WHERE p.permisosId = :permisosId"),
    @NamedQuery(name = "Permisos.findByNombre", query = "SELECT p FROM Permisos p WHERE p.nombre = :nombre")})
public class Permisos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "permisos_id")
    private Integer permisosId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permisosId")
    private Collection<RolPermisos> rolPermisosCollection;

    public Permisos() {
    }

    public Permisos(Integer permisosId) {
        this.permisosId = permisosId;
    }

    public Permisos(Integer permisosId, String nombre) {
        this.permisosId = permisosId;
        this.nombre = nombre;
    }

    public Integer getPermisosId() {
        return permisosId;
    }

    public void setPermisosId(Integer permisosId) {
        this.permisosId = permisosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<RolPermisos> getRolPermisosCollection() {
        return rolPermisosCollection;
    }

    public void setRolPermisosCollection(Collection<RolPermisos> rolPermisosCollection) {
        this.rolPermisosCollection = rolPermisosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permisosId != null ? permisosId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permisos)) {
            return false;
        }
        Permisos other = (Permisos) object;
        if ((this.permisosId == null && other.permisosId != null) || (this.permisosId != null && !this.permisosId.equals(other.permisosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Permisos[ permisosId=" + permisosId + " ]";
    }
    
}
