package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Roles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException; // Necesitas importar esto
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
 */
@Stateless
public class RolesFacade extends AbstractFacade<Roles> implements RolesFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolesFacade() {
        super(Roles.class);
    }
    
    // 🚨 IMPLEMENTACIÓN DEL MÉTODO REQUERIDO PARA EL CSV 🚨
    @Override
    public Roles findByNombreRol(String nombreRol) {
        try {
            // Utilizamos JPQL para buscar el rol donde el campo 'nombreRol' (de la entidad Roles) coincide con el parámetro.
            return (Roles) em.createQuery(
                "SELECT r FROM Roles r WHERE r.nombreRol = :nombre")
                .setParameter("nombre", nombreRol)
                .getSingleResult(); // getSingleResult espera exactamente un resultado

        } catch (NoResultException e) {
            // Si no se encuentra un rol, retorna null para que el ControllerUsuario lo maneje.
            return null;
        } catch (Exception e) {
            // Imprime la pila de errores para diagnóstico si algo más falla.
            e.printStackTrace();
            return null;
        }
    }
}