package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.entities.Roles;
import com.mycompany.learnmate.entities.RolesUsuario;
import com.mycompany.learnmate.entities.EstadoUsuario;

import com.mycompany.learnmate.services.EstadoUsuarioFacadeLocal;
import com.mycompany.learnmate.services.PersonasFacadeLocal;
import com.mycompany.learnmate.services.RolesFacadeLocal;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import com.mycompany.learnmate.services.RolesUsuarioFacadeLocal;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList; // Importar si usas la solución temporal para RolesUsuario
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@Named(value = "controllerUsuario")
@ViewScoped
public class ControllerUsuario implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ControllerUsuario.class.getName());

    // -------------------------------------------------------------------------
    // 🎯 PROPIEDADES PARA EL BINDING Y AUXILIARES 🎯
    // -------------------------------------------------------------------------
    private Personas con;
    private Usuarios nuevoUsuario;
    private String confirmarContrasenna;
    private Roles rolSeleccionado;
    private List<Roles> listaRoles;
    private List<EstadoUsuario> listaEstados;
    private List<Usuarios> listaUsuarios;
    private Part file;

    // -------------------------------------------------------------------------
    // EJB INJECTIONS
    // -------------------------------------------------------------------------
    @EJB private UsuariosFacadeLocal ufl;
    @EJB private PersonasFacadeLocal pfl;
    @EJB private RolesFacadeLocal rfl;
    @EJB private EstadoUsuarioFacadeLocal efl;
    @EJB private RolesUsuarioFacadeLocal rufl;

    // -------------------------------------------------------------------------
    // INICIALIZACIÓN
    // -------------------------------------------------------------------------
    public ControllerUsuario() {
    }

    @PostConstruct
    public void init() {
        con = new Personas();
        nuevoUsuario = new Usuarios();

        listaRoles = rfl.findAll();
        listaEstados = efl.findAll();
        listaUsuarios = ufl.findAll();
    }

    // -------------------------------------------------------------------------
    // 🌐 MÉTODOS DE NEGOCIO 🌐
    // -------------------------------------------------------------------------

    // --------------------------------------------------
    // 🚨 1. IMPLEMENTACIÓN DE obtenerRolPrincipal(Usuarios item) 🚨
    // --------------------------------------------------
    /**
     * Busca y retorna el nombre del rol principal asociado a un usuario.
     * Esto requiere un método de búsqueda en RolesUsuarioFacadeLocal.
     * @param usuario El objeto Usuarios.
     * @return El nombre del rol principal o "N/A" si no se encuentra.
     */
    public String obtenerRolPrincipal(Usuarios usuario) {
        try {
            // Se asume que rufl tiene un método para buscar RolesUsuario por Usuario
            // Dependiendo de tu JPA, puede ser una lista o un solo resultado.
            // Si RolesUsuario es la relación ManyToMany, el método debería ser List<RolesUsuario> findAllByUsuarioId(Usuarios usuario)
            
            // Asumiendo que RolesUsuario tiene un método para obtener todos los roles del usuario:
            List<RolesUsuario> rolesAsociados = rufl.findByUsuario(usuario); 
            
            if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                // Solo se devuelve el nombre del primer rol encontrado
                return rolesAsociados.get(0).getRolId().getNombreRol();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "No se pudo obtener el rol principal para el usuario: " + usuario.getPersona(), e);
        }
        return "N/A";
    }

    // --------------------------------------------------
    // 🚨 2. IMPLEMENTACIÓN DE iniciarEdicion(Usuarios item) 🚨
    // --------------------------------------------------
    /**
     * Prepara el controlador para la edición de un usuario.
     * @param usuario El usuario a editar.
     * @return La ruta a la página de edición.
     */
    public String iniciarEdicion(Usuarios usuario) {
        // Carga las propiedades del usuario seleccionado
        this.nuevoUsuario = usuario; 
        this.con = usuario.getPersona();
        
        // Carga el rol principal para el <h:selectOneMenu>
        try {
            List<RolesUsuario> rolesAsociados = rufl.findByUsuario(usuario); 
            if (rolesAsociados != null && !rolesAsociados.isEmpty()) {
                this.rolSeleccionado = rolesAsociados.get(0).getRolId();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar el rol para edición del usuario: " + usuario.getPersona(), e);
        }
        
        // Redirige al formulario de creación/edición
        return "/views/usuarios/editar.xhtml?faces-redirect=true"; 
    }
    
    // --------------------------------------------------
    // 🚨 3. IMPLEMENTACIÓN DE eliminar(Usuarios item) 🚨
    // --------------------------------------------------
    /**
     * Elimina un usuario, sus roles asociados y su registro de persona (cascade manual).
     * @param usuario El usuario a eliminar.
     */
    public void eliminar(Usuarios usuario) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            // 1. Eliminar RolesUsuario (debe hacerse primero por la FK)
            List<RolesUsuario> rolesAsociados = rufl.findByUsuario(usuario); 
            for (RolesUsuario ru : rolesAsociados) {
                rufl.remove(ru);
            }
            
            // 2. Eliminar Usuario
            ufl.remove(usuario);
            
            // 3. Eliminar Persona
            Personas persona = usuario.getPersona();
            pfl.remove(persona);
            
            // Re-inicializa la lista de usuarios para actualizar la tabla
            init(); 
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Éxito", "Usuario, roles y persona eliminados correctamente."));
            
        } catch (Exception e) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error al Eliminar", "No se pudo eliminar el usuario: " + e.getMessage()));
            LOGGER.log(Level.SEVERE, "Error al eliminar usuario", e);
        }
    }

    // ... (El resto de métodos como actualizarPersona() y procesarUsuariosCsv() se mantienen) ...
    // ... (Los getters y setters se mantienen) ...

    /**
     * Registra una nueva persona y su usuario asociado.
     * Usa setPersona() en lugar de setPersonaId().
     * Usa la lógica de RolesUsuario para manejar el rol (temporalmente, ya que la entidad Usuarios no tiene rolPrincipal).
     */
    public String actualizarPersona() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            // 1. Validar Contraseñas
            if (!nuevoUsuario.getContrasenna().equals(confirmarContrasenna)) {
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error de Contraseña", "Las contraseñas no coinciden."));
                return null;
            }
            
            // Verificar si es Creación o Edición
            if (con.getPersonaId() == null) {
                // CREACIÓN
                // 2. Crear Persona (con)
                pfl.create(con); 
                // 3. Crear Usuario (nuevoUsuario)
                nuevoUsuario.setPersona(con); 
                ufl.create(nuevoUsuario); // Guarda el usuario

                // 4. Crear RolesUsuario
                RolesUsuario ru = new RolesUsuario();
                ru.setRolId(rolSeleccionado);
                ru.setUsuarioId(nuevoUsuario);
                rufl.create(ru);

                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Éxito", "Usuario y persona registrados correctamente."));
            } else {
                // EDICIÓN
                // 2. Actualizar Persona (con)
                pfl.edit(con);
                // 3. Actualizar Usuario
                ufl.edit(nuevoUsuario);

                // 4. Actualizar RolesUsuario (asume que solo tiene uno principal y lo actualiza)
                List<RolesUsuario> rolesAsociados = rufl.findByUsuario(nuevoUsuario);
                if (!rolesAsociados.isEmpty()) {
                    RolesUsuario ru = rolesAsociados.get(0);
                    ru.setRolId(rolSeleccionado);
                    rufl.edit(ru);
                }
                
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Éxito", "Usuario y persona actualizados correctamente."));
            }

            init();
            return "list.xhtml?faces-redirect=true"; // Redirige al listado
            
        } catch (Exception e) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error de Registro/Actualización", "Ocurrió un error: " + e.getMessage()));
            LOGGER.log(Level.SEVERE, "Error al procesar usuario", e);
            return null;
        }
    }


    /**
     * Procesa la carga masiva de usuarios desde un archivo CSV.
     */
    public void procesarUsuariosCsv() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        // ... (Tu código de CSV se mantiene sin cambios) ...
        if (file == null) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un archivo CSV."));
            return;
        }

        try (Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8")) {
            
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader("primerNombre", "segundoNombre", "primerApellido", "segundoApellido", 
                            "tipoDocumento", "identificacion", "correo", "rolPrincipal", 
                            "genero", "fechaNacimiento", "direccion", "telefono")
                .withSkipHeaderRecord()
                .withDelimiter(',')
                .withIgnoreEmptyLines(true)
                .withTrim()
                .parse(reader);

            int contador = 0;
            EstadoUsuario estadoActivo = efl.findByNombreEstado("ACTIVO"); // Asegúrate de tener este método en EJB
            Roles rolPorDefecto = null;

            for (CSVRecord record : records) {
                try {
                    // A. CREAR ENTIDAD PERSONAS
                    Personas p = new Personas();
                    p.setPrimerNombre(record.get("primerNombre"));
                    p.setSegundoNombre(record.get("segundoNombre"));
                    p.setPrimerApellido(record.get("primerApellido"));
                    p.setSegundoApellido(record.get("segundoApellido"));
                    p.setTipoDocumento(record.get("tipoDocumento"));
                    p.setIdentificacion(record.get("identificacion"));
                    p.setGenero(record.get("genero"));
                    p.setDireccion(record.get("direccion"));
                    p.setTelefono(record.get("telefono"));
                    String fechaStr = record.get("fechaNacimiento");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaNac = formatter.parse(fechaStr);
                    p.setFechaNacimiento(fechaNac);
                    
                    pfl.create(p);

                    // B. CREAR ENTIDAD USUARIOS
                    Usuarios u = new Usuarios();
                    u.setPersona(p);
                    u.setCorreo(record.get("correo"));
                    u.setNombreusuario(record.get("identificacion"));
                    u.setContrasenna("Cambiar123*");

                    // Obtener Rol (necesario para RolesUsuario)
                    String nombreRol = record.get("rolPrincipal");
                    rolPorDefecto = rfl.findByNombreRol(nombreRol);

                    if (rolPorDefecto == null) {
                         LOGGER.log(Level.WARNING, "Rol no encontrado: " + nombreRol);
                         throw new IllegalArgumentException("Rol '" + nombreRol + "' no es válido en la línea " + record.getRecordNumber());
                    }
                    
                    u.setEstadoId(estadoActivo);
                    ufl.create(u); // Guarda el usuario

                    // C. CREAR ROLES_USUARIO (Para registrar el rol principal)
                    RolesUsuario ru = new RolesUsuario();
                    ru.setRolId(rolPorDefecto);
                    ru.setUsuarioId(u);
                    rufl.create(ru);

                    contador++;
                } catch (Exception rowE) {
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error en línea " + record.getRecordNumber(), rowE.getMessage()));
                }
            }

            if (contador > 0) {
                // Vuelve a cargar la lista de usuarios para que se muestre inmediatamente
                init(); 
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Carga Completa", contador + " usuarios cargados correctamente."));
            } else {
                 contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                    "Carga Fallida", "No se procesó ningún usuario. Revise el formato y el delimitador."));
            }

        } catch (ConstraintViolationException cve) {
            String violationDetail = "";
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                violationDetail += violation.getPropertyPath() + ": " + violation.getMessage() + " | ";
            }
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Error de Validación (CSV)", "Violación de restricción: " + violationDetail));
        } catch (Exception e) {
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Error de Archivo/Inesperado", "Ocurrió un error grave durante la carga: " + e.getMessage()));
        }
    }


    // -------------------------------------------------------------------------
    // GETTERS Y SETTERS
    // -------------------------------------------------------------------------

    public Roles getRolSeleccionado() { return rolSeleccionado; }
    public void setRolSeleccionado(Roles rolSeleccionado) { this.rolSeleccionado = rolSeleccionado; }
    public Personas getCon() { return con; }
    public void setCon(Personas con) { this.con = con; }
    public Usuarios getNuevoUsuario() { return nuevoUsuario; }
    public void setNuevoUsuario(Usuarios nuevoUsuario) { this.nuevoUsuario = nuevoUsuario; }
    public String getConfirmarContrasenna() { return confirmarContrasenna; }
    public void setConfirmarContrasenna(String confirmarContrasenna) { this.confirmarContrasenna = confirmarContrasenna; }
    public List<Roles> getListaRoles() { return listaRoles; }
    public void setListaRoles(List<Roles> listaRoles) { this.listaRoles = listaRoles; }
    public List<EstadoUsuario> getListaEstados() { return listaEstados; }
    public void setListaEstados(List<EstadoUsuario> listaEstados) { this.listaEstados = listaEstados; }
    public List<Usuarios> getListaUsuarios() { return listaUsuarios; }
    public void setListaUsuarios(List<Usuarios> listaUsuarios) { this.listaUsuarios = listaUsuarios; }
    public Part getFile() { return file; }
    public void setFile(Part file) { this.file = file; }
}