package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Cursos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException; // Import required

/**
 *
 * @author castr
 */
@Stateless
public class CursosFacade extends AbstractFacade<Cursos> implements CursosFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursosFacade() {
        super(Cursos.class);
    }
    
    // ✅ Implementación del método findByNombre(String)
    @Override
    public Cursos findByNombre(String nombreCurso) {
        try {
            // Se asume que el método o NamedQuery se llama 'Cursos.findByNombreCurso'
            // y que la entidad Cursos tiene un campo 'nombreCurso'.
            return em.createNamedQuery("Cursos.findByNombreCurso", Cursos.class)
                    .setParameter("nombreCurso", nombreCurso)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Devuelve null si no se encuentra ningún curso con ese nombre
            return null;
        }
    }
}