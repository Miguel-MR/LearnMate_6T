/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareas.findAll", query = "SELECT t FROM Tareas t"),
    @NamedQuery(name = "Tareas.findByTareaId", query = "SELECT t FROM Tareas t WHERE t.tareaId = :tareaId"),
    @NamedQuery(name = "Tareas.findByTitulo", query = "SELECT t FROM Tareas t WHERE t.titulo = :titulo"),
    @NamedQuery(name = "Tareas.findByFechaEntrega", query = "SELECT t FROM Tareas t WHERE t.fechaEntrega = :fechaEntrega")})
public class Tareas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tarea_id")
    private Integer tareaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo")
    private String titulo;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @JoinColumn(name = "asignacion_id", referencedColumnName = "asignacion_id")
    @ManyToOne(optional = false)
    private AsignacionAsignaturas asignacionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareaId")
    private Collection<EntregasTareas> entregasTareasCollection;

    public Tareas() {
    }

    public Tareas(Integer tareaId) {
        this.tareaId = tareaId;
    }

    public Tareas(Integer tareaId, String titulo, Date fechaEntrega) {
        this.tareaId = tareaId;
        this.titulo = titulo;
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getTareaId() {
        return tareaId;
    }

    public void setTareaId(Integer tareaId) {
        this.tareaId = tareaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public AsignacionAsignaturas getAsignacionId() {
        return asignacionId;
    }

    public void setAsignacionId(AsignacionAsignaturas asignacionId) {
        this.asignacionId = asignacionId;
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
        hash += (tareaId != null ? tareaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tareas)) {
            return false;
        }
        Tareas other = (Tareas) object;
        if ((this.tareaId == null && other.tareaId != null) || (this.tareaId != null && !this.tareaId.equals(other.tareaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Tareas[ tareaId=" + tareaId + " ]";
    }
    
}
