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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursos.findAll", query = "SELECT c FROM Cursos c"),
    @NamedQuery(name = "Cursos.findByCursoId", query = "SELECT c FROM Cursos c WHERE c.cursoId = :cursoId"),
    @NamedQuery(name = "Cursos.findByNombreCurso", query = "SELECT c FROM Cursos c WHERE c.nombreCurso = :nombreCurso")})
public class Cursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "curso_id")
    private Integer cursoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_curso")
    private String nombreCurso;
    @JoinColumn(name = "grado_id", referencedColumnName = "grado_id")
    @ManyToOne(optional = false)
    private Grados gradoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoId")
    private Collection<Estudiantes> estudiantesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursoId")
    private Collection<AsignacionAsignaturas> asignacionAsignaturasCollection;

    public Cursos() {
    }

    public Cursos(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public Cursos(Integer cursoId, String nombreCurso) {
        this.cursoId = cursoId;
        this.nombreCurso = nombreCurso;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Grados getGradoId() {
        return gradoId;
    }

    public void setGradoId(Grados gradoId) {
        this.gradoId = gradoId;
    }

    @XmlTransient
    public Collection<Estudiantes> getEstudiantesCollection() {
        return estudiantesCollection;
    }

    public void setEstudiantesCollection(Collection<Estudiantes> estudiantesCollection) {
        this.estudiantesCollection = estudiantesCollection;
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
        hash += (cursoId != null ? cursoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursos)) {
            return false;
        }
        Cursos other = (Cursos) object;
        if ((this.cursoId == null && other.cursoId != null) || (this.cursoId != null && !this.cursoId.equals(other.cursoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Cursos[ cursoId=" + cursoId + " ]";
    }
    
}
