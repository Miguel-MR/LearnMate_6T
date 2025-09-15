package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.EstadoUsuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException; // You need this import!
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
 */
@Stateless
public class EstadoUsuarioFacade extends AbstractFacade<EstadoUsuario> implements EstadoUsuarioFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoUsuarioFacade() {
        super(EstadoUsuario.class);
    }
    
    // 🚨 IMPLEMENTACIÓN REQUERIDA PARA RESOLVER EL ERROR 🚨
    @Override
    public EstadoUsuario findByNombreEstado(String nombreEstado) {
        try {
            // This query assumes you have defined a NamedQuery in your 
            // EstadoUsuario entity named "EstadoUsuario.findByNombreEstado"
            return em.createNamedQuery("EstadoUsuario.findByNombreEstado", EstadoUsuario.class)
                     .setParameter("nombreEstado", nombreEstado)
                     .getSingleResult();
        } catch (NoResultException e) {
            // If no matching state is found, return null.
            return null; 
        }
    }
}