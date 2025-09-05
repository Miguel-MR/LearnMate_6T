package com.mycompany.learnmate.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Filtro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se necesita inicialización
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest solicitud = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;

        // Evitar cache
        respuesta.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        respuesta.setHeader("Pragma", "no-cache"); // HTTP 1.0
        respuesta.setDateHeader("Expires", 0); // Proxies

        HttpSession sesion = solicitud.getSession(false); // no crear sesión nueva
        String rutaSolicitud = solicitud.getRequestURI();
        String raiz = solicitud.getContextPath();

        // Validaciones
        // 1. Validar el estado de la sesión
        boolean validaSesion = (sesion != null && sesion.getAttribute("usuario") != null);

        // 2. Rutas públicas que no requieren login (inicio y login)
        boolean validaRutaPublica = (rutaSolicitud.equals(raiz + "/") // raíz
                || rutaSolicitud.equals(raiz + "/index.xhtml") // index
                || rutaSolicitud.equals(raiz + "/views/login/login.xhtml") // login
                || rutaSolicitud.equals(raiz + "/views/about/about.xhtml")); // Quienes somos

        // 3. Recursos estáticos
        boolean validaRecurso = (rutaSolicitud.contains("/resources/"));

        if (validaSesion || validaRutaPublica || validaRecurso) {
            // Si la sesión es válida, o la ruta es pública o es recurso estático
            chain.doFilter(request, response);
        } else {
            // Redireccionar a la página de login
            respuesta.sendRedirect(raiz + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {
        // No se necesita limpieza
    }
}
