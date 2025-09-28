package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.entities.Roles;
import com.mycompany.learnmate.entities.RolesUsuario;
import com.mycompany.learnmate.services.EstadoUsuarioFacadeLocal;
import com.mycompany.learnmate.services.PersonasFacadeLocal;
import com.mycompany.learnmate.services.RolesFacadeLocal;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import com.mycompany.learnmate.services.RolesUsuarioFacadeLocal;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Named(value = "controllerUsuario")
@ViewScoped
public class ControllerUsuario implements Serializable {

    private Part file;

    @EJB
    private UsuariosFacadeLocal ufl;
    @EJB
    private PersonasFacadeLocal pfl;
    @EJB
    private RolesFacadeLocal rfl;
    @EJB
    private EstadoUsuarioFacadeLocal efl;
    @EJB
    private RolesUsuarioFacadeLocal rufl;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<Usuarios> listarUsuarios() {
        try {
            return ufl.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void procesarUsuariosCsv() {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            List<String> errores = new ArrayList<>();

            for (CSVRecord record : records) {
                try {
                    // Crear Persona
                    Personas persona = new Personas();
                    persona.setPrimerNombre(record.get("Primer_Nombre"));
                    persona.setSegundoNombre(record.get("Segundo_Nombre"));
                    persona.setPrimerApellido(record.get("Primer_Apellido"));
                    persona.setSegundoApellido(record.get("Segundo_Apellido"));
                    persona.setTipoDocumento(record.get("Tipo_Documento"));
                    persona.setIdentificacion(record.get("Numero_documento"));
                    persona.setCorreoElectronico(record.get("Correo"));
                    persona.setDireccion(record.get("Direccion"));
                    persona.setTelefono(record.get("Numero_Telefono"));

                    // Validar y setear género
                    String generoStr = record.get("Genero");
                    if (!generoStr.equalsIgnoreCase("Masculino") && !generoStr.equalsIgnoreCase("Femenino")) {
                        throw new Exception("Género inválido: " + generoStr);
                    }
                    persona.setGenero(generoStr);

                    // Fecha de nacimiento
                    String fechaStr = record.get("Fecha_Nacimiento");
                    if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        persona.setFechaNacimiento(sdf.parse(fechaStr));
                    }

                    pfl.create(persona);

                    // Crear Usuario
                    Usuarios user = new Usuarios();
                    user.setNombreusuario(record.get("Nombre_Usuario"));
                    user.setContrasenna(hashSHA256(record.get("Contrasena")));
                    user.setEstadoId(efl.find(1));
                    user.setPersona(persona);
                    ufl.create(user);

                    // Asignar Rol
                    int rolId;
                    switch (record.get("Rol")) {
                        case "Administrador":
                            rolId = 1;
                            break;
                        case "Profesor":
                            rolId = 2;
                            break;
                        case "Estudiante":
                            rolId = 3;
                            break;
                        case "Acudiente":
                            rolId = 4;
                            break;
                        default:
                            throw new Exception("Rol desconocido: " + record.get("Rol"));
                    }
                    Roles rol = rfl.find(rolId);
                    RolesUsuario ru = new RolesUsuario();
                    ru.setUsuarioId(user);
                    ru.setRolId(rol);
                    rufl.create(ru);

                } catch (Exception filaEx) {
                    errores.add("Fila " + record.getRecordNumber() + ": " + filaEx.getMessage());
                    filaEx.printStackTrace();
                }
            }

            FacesContext contexto = FacesContext.getCurrentInstance();
            if (errores.isEmpty()) {
                contexto.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios cargados desde CSV correctamente", null));
            } else {
                contexto.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Algunos usuarios no se cargaron:\n" + String.join("\n", errores), null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al procesar CSV: " + e.getMessage(), null));
        }
    }

    private String hashSHA256(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash SHA-256", e);
        }
    }
}
