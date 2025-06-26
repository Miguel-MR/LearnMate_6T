/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author castr
 */
@Entity
@Table(name = "notificaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificaciones.findAll", query = "SELECT n FROM Notificaciones n"),
    @NamedQuery(name = "Notificaciones.findByNotificacionId", query = "SELECT n FROM Notificaciones n WHERE n.notificacionId = :notificacionId"),
    @NamedQuery(name = "Notificaciones.findByNombreNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.nombreNotificacion = :nombreNotificacion"),
    @NamedQuery(name = "Notificaciones.findByFechaNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.fechaNotificacion = :fechaNotificacion"),
    @NamedQuery(name = "Notificaciones.findByCategoriaNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.categoriaNotificacion = :categoriaNotificacion"),
    @NamedQuery(name = "Notificaciones.findByFechaCreacion", query = "SELECT n FROM Notificaciones n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Notificaciones.findByAsunto", query = "SELECT n FROM Notificaciones n WHERE n.asunto = :asunto")})
public class Notificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notificacion_id")
    private Integer notificacionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_notificacion")
    private String nombreNotificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_notificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaNotificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "categoria_notificacion")
    private String categoriaNotificacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "asunto")
    private String asunto;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "profesor_id", referencedColumnName = "profesor_id")
    @ManyToOne(optional = false)
    private Profesores profesorId;

    public Notificaciones() {
    }

    public Notificaciones(Integer notificacionId) {
        this.notificacionId = notificacionId;
    }

    public Notificaciones(Integer notificacionId, String nombreNotificacion, Date fechaNotificacion, String categoriaNotificacion, String asunto) {
        this.notificacionId = notificacionId;
        this.nombreNotificacion = nombreNotificacion;
        this.fechaNotificacion = fechaNotificacion;
        this.categoriaNotificacion = categoriaNotificacion;
        this.asunto = asunto;
    }

    public Integer getNotificacionId() {
        return notificacionId;
    }

    public void setNotificacionId(Integer notificacionId) {
        this.notificacionId = notificacionId;
    }

    public String getNombreNotificacion() {
        return nombreNotificacion;
    }

    public void setNombreNotificacion(String nombreNotificacion) {
        this.nombreNotificacion = nombreNotificacion;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getCategoriaNotificacion() {
        return categoriaNotificacion;
    }

    public void setCategoriaNotificacion(String categoriaNotificacion) {
        this.categoriaNotificacion = categoriaNotificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Profesores getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Profesores profesorId) {
        this.profesorId = profesorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificacionId != null ? notificacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificaciones)) {
            return false;
        }
        Notificaciones other = (Notificaciones) object;
        if ((this.notificacionId == null && other.notificacionId != null) || (this.notificacionId != null && !this.notificacionId.equals(other.notificacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Notificaciones[ notificacionId=" + notificacionId + " ]";
    }
    
}
