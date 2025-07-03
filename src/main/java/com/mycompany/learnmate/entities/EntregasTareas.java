/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "entregas_tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntregasTareas.findAll", query = "SELECT e FROM EntregasTareas e"),
    @NamedQuery(name = "EntregasTareas.findByEntregaId", query = "SELECT e FROM EntregasTareas e WHERE e.entregaId = :entregaId"),
    @NamedQuery(name = "EntregasTareas.findByFechaEntrega", query = "SELECT e FROM EntregasTareas e WHERE e.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "EntregasTareas.findByArchivoEntregado", query = "SELECT e FROM EntregasTareas e WHERE e.archivoEntregado = :archivoEntregado"),
    @NamedQuery(name = "EntregasTareas.findByCalificacion", query = "SELECT e FROM EntregasTareas e WHERE e.calificacion = :calificacion")})
public class EntregasTareas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "entrega_id")
    private Integer entregaId;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Size(max = 255)
    @Column(name = "archivo_entregado")
    private String archivoEntregado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "calificacion")
    private BigDecimal calificacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    @ManyToOne(optional = false)
    private Estudiantes estudianteId;
    @JoinColumn(name = "tarea_id", referencedColumnName = "tarea_id")
    @ManyToOne(optional = false)
    private Tareas tareaId;

    public EntregasTareas() {
    }

    public EntregasTareas(Integer entregaId) {
        this.entregaId = entregaId;
    }

    public Integer getEntregaId() {
        return entregaId;
    }

    public void setEntregaId(Integer entregaId) {
        this.entregaId = entregaId;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getArchivoEntregado() {
        return archivoEntregado;
    }

    public void setArchivoEntregado(String archivoEntregado) {
        this.archivoEntregado = archivoEntregado;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estudiantes getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Estudiantes estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Tareas getTareaId() {
        return tareaId;
    }

    public void setTareaId(Tareas tareaId) {
        this.tareaId = tareaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entregaId != null ? entregaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntregasTareas)) {
            return false;
        }
        EntregasTareas other = (EntregasTareas) object;
        if ((this.entregaId == null && other.entregaId != null) || (this.entregaId != null && !this.entregaId.equals(other.entregaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.EntregasTareas[ entregaId=" + entregaId + " ]";
    }
    
}
