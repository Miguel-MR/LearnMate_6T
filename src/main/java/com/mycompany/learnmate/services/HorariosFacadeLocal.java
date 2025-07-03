/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Horarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface HorariosFacadeLocal {

    void create(Horarios horarios);

    void edit(Horarios horarios);

    void remove(Horarios horarios);

    Horarios find(Object id);

    List<Horarios> findAll();

    List<Horarios> findRange(int[] range);

    int count();
    
}
