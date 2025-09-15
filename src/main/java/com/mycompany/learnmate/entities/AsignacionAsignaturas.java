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
@Table(name = "asignacion_asignaturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionAsignaturas.findAll", query = "SELECT a FROM AsignacionAsignaturas a"),
    @NamedQuery(name = "AsignacionAsignaturas.findByAsignacionId", query = "SELECT a FROM AsignacionAsignaturas a WHERE a.asignacionId = :asignacionId")})
public class AsignacionAsignaturas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "asignacion_id")
    private Integer asignacionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignacionId")
    private Collection<Boletines> boletinesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignacionId")
    private Collection<Tareas> tareasCollection;
    @JoinColumn(name = "asignatura_id", referencedColumnName = "asignatura_id")
    @ManyToOne(optional = false)
    private Asignaturas asignaturaId;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id")
    @ManyToOne(optional = false)
    private Cursos cursoId;
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id")
    @ManyToOne(optional = false)
    private Profesores profesorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignacionId")
    private Collection<Horarios> horariosCollection;

    public AsignacionAsignaturas() {
    }

    public AsignacionAsignaturas(Integer asignacionId) {
        this.asignacionId = asignacionId;
    }

    public Integer getAsignacionId() {
        return asignacionId;
    }

    public void setAsignacionId(Integer asignacionId) {
        this.asignacionId = asignacionId;
    }

    @XmlTransient
    public Collection<Boletines> getBoletinesCollection() {
        return boletinesCollection;
    }

    public void setBoletinesCollection(Collection<Boletines> boletinesCollection) {
        this.boletinesCollection = boletinesCollection;
    }

    @XmlTransient
    public Collection<Tareas> getTareasCollection() {
        return tareasCollection;
    }

    public void setTareasCollection(Collection<Tareas> tareasCollection) {
        this.tareasCollection = tareasCollection;
    }

    public Asignaturas getAsignaturaId() {
        return asignaturaId;
    }

    public void setAsignaturaId(Asignaturas asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public Cursos getCursoId() {
        return cursoId;
    }

    public void setCursoId(Cursos cursoId) {
        this.cursoId = cursoId;
    }

    public Profesores getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesores profesorId) {
        this.profesorId = profesorId;
    }

    @XmlTransient
    public Collection<Horarios> getHorariosCollection() {
        return horariosCollection;
    }

    public void setHorariosCollection(Collection<Horarios> horariosCollection) {
        this.horariosCollection = horariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asignacionId != null ? asignacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignacionAsignaturas)) {
            return false;
        }
        AsignacionAsignaturas other = (AsignacionAsignaturas) object;
        if ((this.asignacionId == null && other.asignacionId != null) || (this.asignacionId != null && !this.asignacionId.equals(other.asignacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.AsignacionAsignaturas[ asignacionId=" + asignacionId + " ]";
    }
    
}
