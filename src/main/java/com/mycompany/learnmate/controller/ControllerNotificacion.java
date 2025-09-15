package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Notificaciones;
import com.mycompany.learnmate.services.NotificacionesFacadeLocal;
import com.mycompany.learnmate.entities.Profesores; // Necesario para la relación
import com.mycompany.learnmate.services.ProfesoresFacadeLocal; // <--- DESCOMENTADO Y REQUERIDO

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;


@Named(value = "controllerNotificacion")
@ViewScoped
public class ControllerNotificacion implements Serializable {

    private Notificaciones notif = new Notificaciones();
    private List<Notificaciones> listaNotificaciones;
    
    // Campos temporales para manejar la entrada del formulario
    private String nombreInstructorTemp;
    private String nombreInstitucionTemp;

    @EJB
    private NotificacionesFacadeLocal nfl;
    
    @EJB // <--- INYECCIÓN AÑADIDA
    private ProfesoresFacadeLocal profesoresEJB;

    @PostConstruct
    public void init() {
        try {
            listaNotificaciones = nfl.findAll();

            // Lógica para cargar notificación si viene ID por parámetro (para edición)
            String param = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("notificacionId");

            if (param != null && !param.trim().isEmpty()) {
                Integer id = Integer.parseInt(param);
                notif = nfl.find(id);

                if (notif == null) {
                    notif = new Notificaciones();
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar notificación", e.getMessage()));
            notif = new Notificaciones();
        }
    }

   public String guardar() {
    try {
        // =======================
        // VALIDACIONES
        // =======================
        if (notif.getNombreNotificacion() == null || notif.getNombreNotificacion().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre es obligatorio."));
            return null;
        }
        if (notif.getFechaNotificacion() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar una fecha."));
            return null;
        }
        if (notif.getCategoriaNotificacion() == null || notif.getCategoriaNotificacion().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe ingresar la categoría."));
            return null;
        }
        if (notif.getAsunto() == null || notif.getAsunto().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El asunto es obligatorio."));
            return null;
        }

        // =======================
        // PROFESOR LOGUEADO
        // =======================
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

        Profesores profesorLogueado = (Profesores) session.getAttribute("profesorLogueado");

        if (profesorLogueado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No hay un profesor logueado."));
            return null;
        }

        notif.setProfesorId(profesorLogueado);

        // =======================
        // CREAR O EDITAR
        // =======================
        if (notif.getNotificacionId() == null) {
            notif.setFechaCreacion(new Date());
            nfl.create(notif);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Notificación creada", null));
        } else {
            nfl.edit(notif);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Notificación actualizada", null));
        }

        notif = new Notificaciones(); // reset
        return "notificaciones.xhtml?faces-redirect=true";

    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al guardar", e.getMessage()));
        return null;
    }
}




    // --- Otros métodos (eliminar, ListarNotificaciones, Getters y Setters) ---
    // ... (El resto de tus métodos se mantienen sin cambios)
    public void eliminar(Notificaciones n) {
        try {
            nfl.remove(n);
            listaNotificaciones.remove(n);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Notificación eliminada", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", e.getMessage()));
        }
    }
    
    public List<Notificaciones> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public List<Notificaciones> ListarNotificaciones() {
        return listaNotificaciones;
    }

    public Notificaciones getNotif() {
        return notif;
    }

    public void setNotif(Notificaciones notif) {
        this.notif = notif;
    }
    
    public String getNombreInstructorTemp() {
        return nombreInstructorTemp;
    }

    public void setNombreInstructorTemp(String nombreInstructorTemp) {
        this.nombreInstructorTemp = nombreInstructorTemp;
    }

    public String getNombreInstitucionTemp() {
        return nombreInstitucionTemp;
    }

    public void setNombreInstitucionTemp(String nombreInstitucionTemp) {
        this.nombreInstitucionTemp = nombreInstitucionTemp;
    }
}