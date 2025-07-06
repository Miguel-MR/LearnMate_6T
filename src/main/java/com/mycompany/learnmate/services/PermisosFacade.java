/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Permisos;
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
public class PermisosFacade extends AbstractFacade<Permisos> implements PermisosFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisosFacade() {
        super(Permisos.class);
    }

    @Override
    public List<Permisos> permisosByUser(Usuarios usuario) {
        try {
            return em.createQuery("SELECT p from Usuarios u"
                    + " JOIN u.rolesUsuarioCollection ruc "
                    + "JOIN ruc.rolId ri"
                    + " JOIN ri.rolPermisosCollection rpc "
                    + "JOIN rpc.permisosId p "
                    + "where u.nombreusuario=:usuario", Permisos.class).setParameter("usuario", usuario.getNombreusuario()).getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
}
