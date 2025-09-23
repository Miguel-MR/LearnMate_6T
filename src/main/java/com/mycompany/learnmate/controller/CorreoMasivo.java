package com.mycompany.learnmate.controller;

import java.util.Properties;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
@RequestScoped
public class CorreoMasivo {

    private String to;
    private String from;
    private String subject;
    private String descrip;
    private String username;
    private String password;
    private String smtp;
    private int port;

    public CorreoMasivo() {
        this.to = null;
        this.from = "diegitoo48@gmail.com";
        this.subject = null;
        this.descrip = null;
        this.username = "diegitoo48@gmail.com";
        this.password = "fjno issp mplo mohv";
        this.smtp = "smtp.gmail.com";
        this.port = 587;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void enviarEmail() {
        Properties props;
        Session session;
        MimeMessage message;
        Address fromAddress;
        Address toAddress = null;

        props = new Properties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // fuerza TLS 1.2
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        message = new MimeMessage(session);
        try {
            message.setContent(getDescrip(), "text/plain");
            message.setSubject(getSubject());
            fromAddress = new InternetAddress(getFrom());
            message.setFrom(fromAddress);
            String[] recipientList = to.split(",");
            InternetAddress[] recipientAddresses = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddresses[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(this.smtp, this.port, this.username, this.password);
            if (!transport.isConnected()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se envio su correo."));

            }
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            System.out.println("Error");
            me.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "No fue enviado el correo."));
        }

    }

}
