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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import java.util.Date;


/**
 *
 * @author yeico
 */
@Entity
@Table(name = "personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByPersonaId", query = "SELECT p FROM Personas p WHERE p.personaId = :personaId"),
    @NamedQuery(name = "Personas.findByTipoDocumento", query = "SELECT p FROM Personas p WHERE p.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Personas.findByIdentificacion", query = "SELECT p FROM Personas p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Personas.findByPrimerNombre", query = "SELECT p FROM Personas p WHERE p.primerNombre = :primerNombre"),
    @NamedQuery(name = "Personas.findBySegundoNombre", query = "SELECT p FROM Personas p WHERE p.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Personas.findByPrimerApellido", query = "SELECT p FROM Personas p WHERE p.primerApellido = :primerApellido"),
    @NamedQuery(name = "Personas.findBySegundoApellido", query = "SELECT p FROM Personas p WHERE p.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Personas.findByGenero", query = "SELECT p FROM Personas p WHERE p.genero = :genero"),
    @NamedQuery(name = "Personas.findByFechaNacimiento", query = "SELECT p FROM Personas p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Personas.findByDireccion", query = "SELECT p FROM Personas p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Personas.findByTelefono", query = "SELECT p FROM Personas p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personas.findByCorreoElectronico", query = "SELECT p FROM Personas p WHERE p.correoElectronico = :correoElectronico")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "persona_id")
    private Integer personaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 50)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 50)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "genero")
    private String genero;
    @Past // valida que la fecha sea pasada
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 100)
    @Column(name = "correo_electronico")
    private String correoElectronico;

    public Personas() {
    }

    public Personas(Integer personaId) {
        this.personaId = personaId;
    }

    public Personas(Integer personaId, String tipoDocumento, String identificacion, String primerNombre, String primerApellido, String genero) {
        this.personaId = personaId;
        this.tipoDocumento = tipoDocumento;
        this.identificacion = identificacion;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.genero = genero;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personaId != null ? personaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.personaId == null && other.personaId != null) || (this.personaId != null && !this.personaId.equals(other.personaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.learnmate.entities.Personas[ personaId=" + personaId + " ]";
    }

}
