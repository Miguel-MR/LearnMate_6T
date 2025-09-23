
package com.mycompany.learnmate.controller;

import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailService {

    private final String host = "smtp.gmail.com";
    private final String puerto = "587";
    private final String usuario = "diegitoo48@gmail.com"; // cambia
    private final String clave = "fjno issp mplo mohv"; // usa clave de aplicación

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });
    }

    public void enviarCorreo(String destinatario, String asunto, String mensaje) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(usuario));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(mensaje);
        Transport.send(message);
    }

    public void enviarCorreoMasivo(List<String> destinatarios, String asunto, String mensaje) throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(usuario));

        InternetAddress[] toAddresses = destinatarios.stream()
                .map(correo -> {
                    try {
                        return new InternetAddress(correo);
                    } catch (AddressException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(InternetAddress[]::new);

        message.setRecipients(Message.RecipientType.BCC, toAddresses); // BCC para ocultar correos
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);
    }
}
