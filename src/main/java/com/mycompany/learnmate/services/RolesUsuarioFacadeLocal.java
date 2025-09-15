/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.RolesUsuario;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface RolesUsuarioFacadeLocal {

    void create(RolesUsuario rolesUsuario);

    void edit(RolesUsuario rolesUsuario);

    void remove(RolesUsuario rolesUsuario);

    RolesUsuario find(Object id);

    List<RolesUsuario> findAll();

    List<RolesUsuario> findRange(int[] range);

    int count();

    public List<RolesUsuario> findByUsuario(Usuarios u);
    
}
