/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.AsignacionAsignaturas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface AsignacionAsignaturasFacadeLocal {

    void create(AsignacionAsignaturas asignacionAsignaturas);

    void edit(AsignacionAsignaturas asignacionAsignaturas);

    void remove(AsignacionAsignaturas asignacionAsignaturas);

    AsignacionAsignaturas find(Object id);

    List<AsignacionAsignaturas> findAll();

    List<AsignacionAsignaturas> findRange(int[] range);

    int count();
    
}
