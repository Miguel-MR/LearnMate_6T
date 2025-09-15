    package com.mycompany.learnmate.controller;

    import javax.enterprise.context.RequestScoped;
    import javax.inject.Named;
    import java.io.*;
    import java.net.HttpURLConnection;
    import java.net.URL;

    @Named
    @RequestScoped
    public class CorreoBean {

        private String asunto;
        private String mensaje;

        // Getters y setters
        public String getAsunto() { return asunto; }
        public void setAsunto(String asunto) { this.asunto = asunto; }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }

        // Método para enviar correos usando la API Flask
        public String enviarCorreos() {
            try {
                URL url = new URL("http://127.0.0.1:5000/enviar-correos");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                // JSON que espera tu API Flask
                String jsonInputString = String.format(
                    "{\"asunto\":\"%s\",\"mensaje\":\"%s\"}",
                    asunto, mensaje.replace("\"", "\\\"")
                );

                // Enviar datos al servidor
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Leer respuesta del servidor
                StringBuilder respuesta = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        respuesta.append(responseLine.trim());
                    }
                }

                int code = conn.getResponseCode();
                System.out.println("Código de respuesta: " + code);
                System.out.println("Respuesta: " + respuesta);

                if (code == 200) {
                    // Redirige a una página de éxito en JSF
                    return "enviarCorreos.xhtml?faces-redirect=true";
                } else {
                    throw new RuntimeException("Error en API: " + code + " - " + respuesta);
                }

                
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Permite manejar el error en JSF
            }
        }
    }
