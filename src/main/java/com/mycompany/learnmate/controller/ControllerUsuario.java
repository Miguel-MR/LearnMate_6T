/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.services.EstadoUsuarioFacadeLocal;
import com.mycompany.learnmate.services.PersonasFacadeLocal;
import com.mycompany.learnmate.services.RolesFacadeLocal;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author castr
 */
@Named(value = "controllerUsuario")
@ViewScoped
public class ControllerUsuario implements Serializable {

    private Part file;
    Usuarios con = new Usuarios();
    @EJB
    UsuariosFacadeLocal cfl;
    @EJB
    PersonasFacadeLocal pfl;
    @EJB
    RolesFacadeLocal rfl;
    @EJB
    EstadoUsuarioFacadeLocal efl;

    public Usuarios getCon() {
        return con;
    }

    public void setCon(Usuarios con) {
        this.con = con;
    }

    public List<Usuarios> listarUsuarios() {
        try {
            return cfl.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    
    public void procesarUsuariosCsv() {
        
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                Usuarios user = new Usuarios();
                Personas usuario = new Personas();
                user.setNombreusuario(record.get("Nombre_Usuario"));
                user.setContrasenna(record.get("Contrasena"));
                user.setEstadoId(efl.find(1));
                usuario.setPrimerNombre(record.get("Primer_Nombre"));
                usuario.setSegundoNombre(record.get("Segundo_nombre"));
                usuario.setPrimerApellido(record.get("Primer_Apellido"));
                usuario.setSegundoApellido(record.get("Segundo_apellido"));
                usuario.setTipoDocumento(record.get("Tipo_Documento"));
                usuario.setIdentificacion(record.get("Numero_documento"));
                usuario.setCorreoElectronico(record.get("Correo"));
                usuario.setDireccion(record.get("Direccion"));
                usuario.setTelefono(record.get("Numero_Telefona"));
                usuario.setGenero(record.get("Genero"));
                

                int rol;
                switch (record.get("Rol")) {
                    case "Administrador":
                        rol = 1;
                        break;
                    case "Profesor":
                        rol = 2;
                        break;
                    case "Estudiante":
                        rol = 3;
                        break;
                    case "Acudiente":
                        rol = 4;
                        break;
                    default:
                        throw new AssertionError();
                }
                user.setIdRol(rfl.find(rol));

                usuario.setFechaNacimiento(record.get("Fecha_Nacimiento"));

                cfl.create(user);

                pfl.create(usuario);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios cargados desde CSV correctamente", null));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al procesar CSV: " + e.getMessage(), null));

        }
    }
}
