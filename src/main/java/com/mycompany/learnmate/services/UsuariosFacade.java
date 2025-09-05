/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
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

    @Override
    public Usuarios iniciarSesion(String nombreusuario, String contrasenna) {
        try {
            return em.createQuery("SELECT u FROM Usuarios u WHERE U.estadoId.estadoId=1 AND u.nombreusuario = :usuario AND u.contrasenna = :pass", Usuarios.class)
                    .setParameter("usuario", nombreusuario)
                    .setParameter("pass", contrasenna)
                    .getSingleResult();
        } catch (Exception e) {
           
            return null;
        }
    }

    @Override
    public List<Usuarios> findByPersonaId(Personas persona) {
        return em.createQuery("SELECT u FROM Usuarios u WHERE u.personaId = :persona", Usuarios.class)
                .setParameter("persona", persona)
                .getResultList();
    }
}
