/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "boletines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boletines.findAll", query = "SELECT b FROM Boletines b"),
    @NamedQuery(name = "Boletines.findByBoletinId", query = "SELECT b FROM Boletines b WHERE b.boletinId = :boletinId"),
    @NamedQuery(name = "Boletines.findByPeriodo", query = "SELECT b FROM Boletines b WHERE b.periodo = :periodo"),
    @NamedQuery(name = "Boletines.findByNota", query = "SELECT b FROM Boletines b WHERE b.nota = :nota")})
public class Boletines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "boletin_id")
    private Integer boletinId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo")
    private Character periodo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota")
    private BigDecimal nota;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "asignacion_id", referencedColumnName = "asignacion_id")
    @ManyToOne(optional = false)
    private AsignacionAsignaturas asignacionId;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    @ManyToOne(optional = false)
    private Estudiantes estudianteId;

    public Boletines() {
    }

    public Boletines(Integer boletinId) {
        this.boletinId = boletinId;
    }

    public Boletines(Integer boletinId, Character periodo, BigDecimal nota) {
        this.boletinId = boletinId;
        this.periodo = periodo;
        this.nota = nota;
    }

    public Integer getBoletinId() {
        return boletinId;
    }

    public void setBoletinId(Integer boletinId) {
        this.boletinId = boletinId;
    }

    public Character getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Character periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public AsignacionAsignaturas getAsignacionId() {
        return asignacionId;
    }

    public void setAsignacionId(AsignacionAsignaturas asignacionId) {
        this.asignacionId = asignacionId;
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
        hash += (boletinId != null ? boletinId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boletines)) {
            return false;
        }
        Boletines other = (Boletines) object;
        if ((this.boletinId == null && other.boletinId != null) || (this.boletinId != null && !this.boletinId.equals(other.boletinId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Boletines[ boletinId=" + boletinId + " ]";
    }
    
}
