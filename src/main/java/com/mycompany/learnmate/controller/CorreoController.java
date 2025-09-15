/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.controller;

/**
 *
 * @author One PC
 */
import com.mycompany.learnmate.services.EmailService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class CorreoController {

    private String asunto;
    private String mensaje;
    private String destinatarios; // separados por comas

    private EmailService emailService = new EmailService();

    public void enviar() {
        try {
            List<String> lista = Arrays.asList(destinatarios.split(","));
            emailService.enviarCorreosMasivos(lista, asunto, mensaje);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correos enviados con éxito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al enviar correos"));
            e.printStackTrace();
        }
    }

    // Getters y Setters
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getDestinatarios() { return destinatarios; }
    public void setDestinatarios(String destinatarios) { this.destinatarios = destinatarios; }
}