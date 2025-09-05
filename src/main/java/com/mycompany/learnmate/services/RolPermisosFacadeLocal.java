/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.RolPermisos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface RolPermisosFacadeLocal {

    void create(RolPermisos rolPermisos);

    void edit(RolPermisos rolPermisos);

    void remove(RolPermisos rolPermisos);

    RolPermisos find(Object id);

    List<RolPermisos> findAll();

    List<RolPermisos> findRange(int[] range);

    int count();
    
}
