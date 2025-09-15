/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.RolesUsuario;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author castr
 */
@Stateless
public class RolesUsuarioFacade extends AbstractFacade<RolesUsuario> implements RolesUsuarioFacadeLocal {

    @PersistenceContext(unitName = "learnmatePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolesUsuarioFacade() {
        super(RolesUsuario.class);
    }

    @Override
    public List<RolesUsuario> findByUsuario(Usuarios usuario) {
        return em.createQuery("SELECT r FROM RolesUsuario r WHERE r.usuarioId = :usuario", RolesUsuario.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void create(RolesUsuario ru) {
        super.create(ru);
    }

}
