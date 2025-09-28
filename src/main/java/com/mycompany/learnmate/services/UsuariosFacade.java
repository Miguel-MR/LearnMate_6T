package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Facade para la entidad Usuarios.
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    /**
     * Método para iniciar sesión. Solo busca usuarios activos (estadoId = 1)
     * con nombre y contraseña coincidentes.
     */
    @Override
    public Usuarios iniciarSesion(String nombreusuario, String contrasenna) {
        try {
            return em.createQuery(
                    "SELECT u FROM Usuarios u WHERE u.estadoId.estadoId = 1 AND u.nombreusuario = :usuario AND u.contrasenna = :pass",
                    Usuarios.class)
                    .setParameter("usuario", nombreusuario)
                    .setParameter("pass", contrasenna)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // usuario no encontrado
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene todos los usuarios asociados a una persona.
     */
    @Override
    public List<Usuarios> findByPersonaId(Personas persona) {
        return em.createQuery("SELECT u FROM Usuarios u WHERE u.persona = :persona", Usuarios.class)
                .setParameter("persona", persona)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void create(Usuarios usuario) {
        super.create(usuario);
    }
}
