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
    
    // 🛑 CORRECCIÓN CLAVE 1: El método 'create' DEBE usar el comportamiento de transacción por defecto (REQUIRED).
    // Eliminamos la anotación @REQUIRES_NEW de aquí.
    @Override
    public void create(Usuarios usuario) {
        super.create(usuario);
    }
    
    // 🏆 CORRECCIÓN CLAVE 2: Implementación de createAndFlush para la sincronización.
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public Usuarios createAndFlush(Usuarios usuario) {
        // 1. Persiste el objeto en el contexto de persistencia de la nueva transacción.
        super.create(usuario);
        
        // 2. 🔑 FUERZA EL INSERT en la base de datos para generar y asignar el ID.
        getEntityManager().flush();
        
        // 3. Retorna el objeto Managed con el usuario_id.
        return usuario;
    }
    
    // ... (Mantener métodos iniciarSesion y findByPersonaId sin cambios) ...

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
}