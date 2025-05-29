package com.mycompany.proyecto02.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*") // Filtra todas las páginas protegidas
public class Filtro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest solicitud = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;
        HttpSession sesion = solicitud.getSession(false);

        String rutaSolicitud = solicitud.getRequestURI();
        String raiz = solicitud.getContextPath();

        // Validaciones clave para seguridad y manejo de recursos
        boolean validaSesion = (sesion != null && sesion.getAttribute("usuario") != null);
        boolean validaRutaLogin = (rutaSolicitud.equals(raiz + "/") || rutaSolicitud.equals(raiz + "/login.xhtml"));
        boolean validaRecurso = rutaSolicitud.contains("/resources/") || rutaSolicitud.contains(".css") || rutaSolicitud.contains(".js");

        if (validaSesion || validaRutaLogin || validaRecurso) {
            chain.doFilter(request, response);
        } else {
            respuesta.sendRedirect(raiz + "/login.xhtml");
            return;
        }

        // Evitar almacenamiento en caché
        respuesta.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        respuesta.setHeader("Pragma", "no-cache");
        respuesta.setDateHeader("Expires", 0);
    }

    @Override
    public void destroy() {}
}