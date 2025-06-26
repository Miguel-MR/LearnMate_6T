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
@Table(name = "estudiantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiantes.findAll", query = "SELECT e FROM Estudiantes e"),
    @NamedQuery(name = "Estudiantes.findByEstudianteId", query = "SELECT e FROM Estudiantes e WHERE e.estudianteId = :estudianteId")})
public class Estudiantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "estudiante_id")
    private Integer estudianteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId")
    private Collection<Boletines> boletinesCollection;
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id")
    @ManyToOne(optional = false)
    private Cursos cursoId;
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    @ManyToOne(optional = false)
    private Personas personaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId")
    private Collection<EstudiantesAcudientes> estudiantesAcudientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteId")
    private Collection<EntregasTareas> entregasTareasCollection;

    public Estudiantes() {
    }

    public Estudiantes(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    @XmlTransient
    public Collection<Boletines> getBoletinesCollection() {
        return boletinesCollection;
    }

    public void setBoletinesCollection(Collection<Boletines> boletinesCollection) {
        this.boletinesCollection = boletinesCollection;
    }

    public Cursos getCursoId() {
        return cursoId;
    }

    public void setCursoId(Cursos cursoId) {
        this.cursoId = cursoId;
    }

    public Personas getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Personas personaId) {
        this.personaId = personaId;
    }

    @XmlTransient
    public Collection<EstudiantesAcudientes> getEstudiantesAcudientesCollection() {
        return estudiantesAcudientesCollection;
    }

    public void setEstudiantesAcudientesCollection(Collection<EstudiantesAcudientes> estudiantesAcudientesCollection) {
        this.estudiantesAcudientesCollection = estudiantesAcudientesCollection;
    }

    @XmlTransient
    public Collection<EntregasTareas> getEntregasTareasCollection() {
        return entregasTareasCollection;
    }

    public void setEntregasTareasCollection(Collection<EntregasTareas> entregasTareasCollection) {
        this.entregasTareasCollection = entregasTareasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estudianteId != null ? estudianteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiantes)) {
            return false;
        }
        Estudiantes other = (Estudiantes) object;
        if ((this.estudianteId == null && other.estudianteId != null) || (this.estudianteId != null && !this.estudianteId.equals(other.estudianteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Estudiantes[ estudianteId=" + estudianteId + " ]";
    }
    
}
