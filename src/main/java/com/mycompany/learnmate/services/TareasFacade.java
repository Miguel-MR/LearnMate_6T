/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Tareas;
import com.mycompany.learnmate.entities.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
 */
@Stateless
public class TareasFacade extends AbstractFacade<Tareas> implements TareasFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareasFacade() {
        super(Tareas.class);
    }

    @Override
    public Usuarios iniciarSesion(String nombreusuario, String contrasenna) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
