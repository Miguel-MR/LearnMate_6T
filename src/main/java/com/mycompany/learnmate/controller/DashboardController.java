/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("dashboardController")
@ViewScoped
public class DashboardController implements Serializable {

    @EJB
    private UsuariosFacadeLocal usuariosFacade;

    private int totalUsuarios;

    @PostConstruct
    public void init() {
        totalUsuarios = usuariosFacade.count();
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }
}
    