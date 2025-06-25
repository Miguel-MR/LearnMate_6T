/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author castr
 */
@Named(value = "controllerUsuario")
@ViewScoped
public class ControllerUsuario implements Serializable {
Usuarios con = new Usuarios();
@EJB
UsuariosFacadeLocal cfl;

    public Usuarios getCon() {
        return con;
    }

    public void setCon(Usuarios con) {
        this.con = con;
    }

public List<Usuarios> listarUsuarios() {
       try {
            return cfl.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    
}
}
