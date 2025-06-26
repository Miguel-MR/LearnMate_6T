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
@Table(name = "grados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grados.findAll", query = "SELECT g FROM Grados g"),
    @NamedQuery(name = "Grados.findByGradoId", query = "SELECT g FROM Grados g WHERE g.gradoId = :gradoId"),
    @NamedQuery(name = "Grados.findByNombreGrado", query = "SELECT g FROM Grados g WHERE g.nombreGrado = :nombreGrado")})
public class Grados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grado_id")
    private Integer gradoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_grado")
    private String nombreGrado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gradoId")
    private Collection<Cursos> cursosCollection;

    public Grados() {
    }

    public Grados(Integer gradoId) {
        this.gradoId = gradoId;
    }

    public Grados(Integer gradoId, String nombreGrado) {
        this.gradoId = gradoId;
        this.nombreGrado = nombreGrado;
    }

    public Integer getGradoId() {
        return gradoId;
    }

    public void setGradoId(Integer gradoId) {
        this.gradoId = gradoId;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    @XmlTransient
    public Collection<Cursos> getCursosCollection() {
        return cursosCollection;
    }

    public void setCursosCollection(Collection<Cursos> cursosCollection) {
        this.cursosCollection = cursosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradoId != null ? gradoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grados)) {
            return false;
        }
        Grados other = (Grados) object;
        if ((this.gradoId == null && other.gradoId != null) || (this.gradoId != null && !this.gradoId.equals(other.gradoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Grados[ gradoId=" + gradoId + " ]";
    }
    
}
