/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author castr
 */
public class Filtro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest solicitud = (HttpServletRequest) request;
        HttpServletResponse respuesta = (HttpServletResponse) response;

        respuesta.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        respuesta.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        respuesta.setDateHeader("Expires", 0); // Proxies.

        HttpSession sesion = solicitud.getSession();
        String rutaSolicitud = solicitud.getRequestURI();
        String raiz = solicitud.getContextPath();

        //Validaciones
        //1. Validar el estado de la sesion
        boolean validaSesion = ((sesion) != null && (sesion.getAttribute("usuario") != null));
        //2. Validacion login
        boolean validaRutaLogin = (rutaSolicitud.equals(raiz + "/") || rutaSolicitud.equals(raiz + "/login.xhtml"));
        //3. Cargue de contenido estatico
        boolean validaRecurso = (rutaSolicitud.contains("/resources/"));

        if (validaSesion || validaRutaLogin || validaRecurso) {
            //Si la sesion es valida o la ruta es de login o es un recurso estatico
            chain.doFilter(request, response);
        } else {
            //Redireccionar a la pagina de login
            respuesta.sendRedirect(raiz);
            
        }
    }

    @Override
    public void destroy() {

    }

}
