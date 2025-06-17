/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author castr
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_learnmate_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario iniciarSesion(String usuario, String contrasenna) {
       Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :usuario AND u.contrasena = :contrasenna");
         query.setParameter("usuario", usuario);
            query.setParameter("contrasenna", contrasenna);
            try {
                return (Usuario) query.getSingleResult();
            } catch (Exception e) {
            }

            Usuario usuarioIn = new Usuario();
            return usuarioIn;
        }
    
}
