/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "roles_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolesUsuario.findAll", query = "SELECT r FROM RolesUsuario r"),
    @NamedQuery(name = "RolesUsuario.findByUsuarioRolId", query = "SELECT r FROM RolesUsuario r WHERE r.usuarioRolId = :usuarioRolId")})
public class RolesUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario_rol_id")
    private Integer usuarioRolId;
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    @ManyToOne(optional = false)
    private Roles rolId;
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    @ManyToOne(optional = false)
    private Usuarios usuarioId;

    public RolesUsuario() {
    }

    public RolesUsuario(Integer usuarioRolId) {
        this.usuarioRolId = usuarioRolId;
    }

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
        int hash = 0;
        hash += (usuarioRolId != null ? usuarioRolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolesUsuario)) {
            return false;
        }
        RolesUsuario other = (RolesUsuario) object;
        if ((this.usuarioRolId == null && other.usuarioRolId != null) || (this.usuarioRolId != null && !this.usuarioRolId.equals(other.usuarioRolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.RolesUsuario[ usuarioRolId=" + usuarioRolId + " ]";
    }
    
}
