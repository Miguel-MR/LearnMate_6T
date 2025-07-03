/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Asignaturas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface AsignaturasFacadeLocal {

    void create(Asignaturas asignaturas);

    void edit(Asignaturas asignaturas);

    void remove(Asignaturas asignaturas);

    Asignaturas find(Object id);

    List<Asignaturas> findAll();

    List<Asignaturas> findRange(int[] range);

    int count();
    
}
