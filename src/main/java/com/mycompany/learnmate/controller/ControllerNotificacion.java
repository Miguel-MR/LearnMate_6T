package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Notificaciones;
import com.mycompany.learnmate.services.NotificacionesFacadeLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "controllerNotificacion")
@ViewScoped
public class ControllerNotificacion implements Serializable {

    private Notificaciones notif = new Notificaciones();
    private List<Notificaciones> listaNotificaciones;

    @EJB
    private NotificacionesFacadeLocal nfl;

    @PostConstruct
    public void init() {
        try {
            listaNotificaciones = nfl.findAll();

            // Cargar notificación si viene ID por parámetro
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

            return "notificaciones.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al guardar", e.getMessage()));
            return null;
        }
    }

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
        return listaNotificaciones; // Ya inicializada en @PostConstruct
    }

    public Notificaciones getNotif() {
        return notif;
    }

    public void setNotif(Notificaciones notif) {
        this.notif = notif;
    }
}