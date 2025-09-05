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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "estudiantes_acudientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstudiantesAcudientes.findAll", query = "SELECT e FROM EstudiantesAcudientes e"),
    @NamedQuery(name = "EstudiantesAcudientes.findById", query = "SELECT e FROM EstudiantesAcudientes e WHERE e.id = :id"),
    @NamedQuery(name = "EstudiantesAcudientes.findByParentesco", query = "SELECT e FROM EstudiantesAcudientes e WHERE e.parentesco = :parentesco")})
public class EstudiantesAcudientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "parentesco")
    private String parentesco;
    @JoinColumn(name = "acudiente_id", referencedColumnName = "acudiente_id")
    @ManyToOne(optional = false)
    private Acudientes acudienteId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    @ManyToOne(optional = false)
    private Estudiantes estudianteId;

    public EstudiantesAcudientes() {
    }

    public EstudiantesAcudientes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Acudientes getAcudienteId() {
        return acudienteId;
    }

    public void setAcudienteId(Acudientes acudienteId) {
        this.acudienteId = acudienteId;
    }

    public Estudiantes getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiantes estudianteId) {
        this.estudianteId = estudianteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudiantesAcudientes)) {
            return false;
        }
        EstudiantesAcudientes other = (EstudiantesAcudientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.EstudiantesAcudientes[ id=" + id + " ]";
    }
    
}
