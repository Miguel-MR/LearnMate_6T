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
@Table(name = "asignaturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturas.findAll", query = "SELECT a FROM Asignaturas a"),
    @NamedQuery(name = "Asignaturas.findByAsignaturaId", query = "SELECT a FROM Asignaturas a WHERE a.asignaturaId = :asignaturaId"),
    @NamedQuery(name = "Asignaturas.findByNombreAsignatura", query = "SELECT a FROM Asignaturas a WHERE a.nombreAsignatura = :nombreAsignatura")})
public class Asignaturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "asignatura_id")
    private Integer asignaturaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_asignatura")
    private String nombreAsignatura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturaId")
    private Collection<AsignacionAsignaturas> asignacionAsignaturasCollection;

    public Asignaturas() {
    }

    public Asignaturas(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Asignaturas(Integer asignaturaId, String nombreAsignatura) {
        this.asignaturaId = asignaturaId;
        this.nombreAsignatura = nombreAsignatura;
    }

    public Integer getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Integer asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    @XmlTransient
    public Collection<AsignacionAsignaturas> getAsignacionAsignaturasCollection() {
        return asignacionAsignaturasCollection;
    }

    public void setAsignacionAsignaturasCollection(Collection<AsignacionAsignaturas> asignacionAsignaturasCollection) {
        this.asignacionAsignaturasCollection = asignacionAsignaturasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignaturaId != null ? asignaturaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturas)) {
            return false;
        }
        Asignaturas other = (Asignaturas) object;
        if ((this.asignaturaId == null && other.asignaturaId != null) || (this.asignaturaId != null && !this.asignaturaId.equals(other.asignaturaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Asignaturas[ asignaturaId=" + asignaturaId + " ]";
    }
    
}
