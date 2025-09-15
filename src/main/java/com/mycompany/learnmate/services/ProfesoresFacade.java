/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Profesores;
import com.mycompany.learnmate.entities.Personas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 *
 * @author castr
 */
@Stateless
public class ProfesoresFacade extends AbstractFacade<Profesores> implements ProfesoresFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesoresFacade() {
        super(Profesores.class);
    }
    
     @Override
    public Profesores findByPersona(Personas persona) {
        try {
            return em.createQuery("SELECT p FROM Profesores p WHERE p.persona = :persona", Profesores.class)
                     .setParameter("persona", persona)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
