/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Grados;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
 */
@Stateless
public class GradosFacade extends AbstractFacade<Grados> implements GradosFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GradosFacade() {
        super(Grados.class);
    }
    
}
