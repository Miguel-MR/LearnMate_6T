package com.mycompany.learnmate.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "estado_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoUsuario.findAll", query = "SELECT e FROM EstadoUsuario e"),
    @NamedQuery(name = "EstadoUsuario.findByEstadoId", query = "SELECT e FROM EstadoUsuario e WHERE e.estadoId = :estadoId"),
    @NamedQuery(name = "EstadoUsuario.findByNombreEstado", query = "SELECT e FROM EstadoUsuario e WHERE e.nombreEstado = :nombreEstado")
})
public class EstadoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_id")
    private Integer estadoId;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_estado")
    private String nombreEstado;

    @OneToMany(mappedBy = "estadoId") // coincide con Usuarios.estadoUsuario
    private List<Usuarios> usuariosCollection;

    public EstadoUsuario() {
    }

    public EstadoUsuario(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public EstadoUsuario(Integer estadoId, String nombreEstado) {
        this.estadoId = estadoId;
        this.nombreEstado = nombreEstado;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(List<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        return estadoId != null ? estadoId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EstadoUsuario)) return false;
        EstadoUsuario other = (EstadoUsuario) obj;
        return estadoId != null && estadoId.equals(other.estadoId);
    }

    @Override
    public String toString() {
        return "EstadoUsuario[ estadoId=" + estadoId + ", nombreEstado=" + nombreEstado + " ]";
    }
}
