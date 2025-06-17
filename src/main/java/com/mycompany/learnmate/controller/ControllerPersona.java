/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Persona;
import com.mycompany.learnmate.services.PersonaFacadeLocal;
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
@Named(value = "controllerPersona")
@ViewScoped
public class ControllerPersona implements Serializable {
Persona con = new Persona();
@EJB
PersonaFacadeLocal cfl;

    public Persona getCon() {
        return con;
    }

    public void setCon(Persona con) {
        this.con = con;
    }

public List<Persona> listarPersonas() {
       try {
            return cfl.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    
}
}
