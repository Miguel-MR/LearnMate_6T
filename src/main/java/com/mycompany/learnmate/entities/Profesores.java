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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "profesores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesores.findAll", query = "SELECT p FROM Profesores p"),
    @NamedQuery(name = "Profesores.findByProfesorId", query = "SELECT p FROM Profesores p WHERE p.profesorId = :profesorId")})
public class Profesores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "profesor_id")
    private Integer profesorId;
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    @ManyToOne(optional = false)
    private Personas personaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorId")
    private Collection<AsignacionAsignaturas> asignacionAsignaturasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profesorId")
    private Collection<Notificaciones> notificacionesCollection;

    public Profesores() {
    }

    public Profesores(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public Personas getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Personas personaId) {
        this.personaId = personaId;
    }

    @XmlTransient
    public Collection<AsignacionAsignaturas> getAsignacionAsignaturasCollection() {
        return asignacionAsignaturasCollection;
    }

    public void setAsignacionAsignaturasCollection(Collection<AsignacionAsignaturas> asignacionAsignaturasCollection) {
        this.asignacionAsignaturasCollection = asignacionAsignaturasCollection;
    }

    @XmlTransient
    public Collection<Notificaciones> getNotificacionesCollection() {
        return notificacionesCollection;
    }

    public void setNotificacionesCollection(Collection<Notificaciones> notificacionesCollection) {
        this.notificacionesCollection = notificacionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profesorId != null ? profesorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesores)) {
            return false;
        }
        Profesores other = (Profesores) object;
        if ((this.profesorId == null && other.profesorId != null) || (this.profesorId != null && !this.profesorId.equals(other.profesorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Profesores[ profesorId=" + profesorId + " ]";
    }
    
}
