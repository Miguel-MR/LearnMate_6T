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
@Table(name = "acudientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acudientes.findAll", query = "SELECT a FROM Acudientes a"),
    @NamedQuery(name = "Acudientes.findByAcudienteId", query = "SELECT a FROM Acudientes a WHERE a.acudienteId = :acudienteId")})
public class Acudientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acudiente_id")
    private Integer acudienteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acudienteId")
    private Collection<EstudiantesAcudientes> estudiantesAcudientesCollection;
    @JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
    @ManyToOne(optional = false)
    private Personas personaId;

    public Acudientes() {
    }

    public Acudientes(Integer acudienteId) {
        this.acudienteId = acudienteId;
    }

    public Integer getAcudienteId() {
        return acudienteId;
    }

    public void setAcudienteId(Integer acudienteId) {
        this.acudienteId = acudienteId;
    }

    @XmlTransient
    public Collection<EstudiantesAcudientes> getEstudiantesAcudientesCollection() {
        return estudiantesAcudientesCollection;
    }

    public void setEstudiantesAcudientesCollection(Collection<EstudiantesAcudientes> estudiantesAcudientesCollection) {
        this.estudiantesAcudientesCollection = estudiantesAcudientesCollection;
    }

    public Personas getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Personas personaId) {
        this.personaId = personaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acudienteId != null ? acudienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acudientes)) {
            return false;
        }
        Acudientes other = (Acudientes) object;
        if ((this.acudienteId == null && other.acudienteId != null) || (this.acudienteId != null && !this.acudienteId.equals(other.acudienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Acudientes[ acudienteId=" + acudienteId + " ]";
    }
    
}
