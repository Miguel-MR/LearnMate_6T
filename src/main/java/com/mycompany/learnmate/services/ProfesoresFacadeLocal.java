/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Profesores;
import com.mycompany.learnmate.entities.Personas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface ProfesoresFacadeLocal {

    void create(Profesores profesores);

    void edit(Profesores profesores);

    void remove(Profesores profesores);

    Profesores find(Object id);

    List<Profesores> findAll();

    List<Profesores> findRange(int[] range);

    int count();
      Profesores findByPersona(Personas persona);
    
}
