/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.EstudiantesAcudientes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface EstudiantesAcudientesFacadeLocal {

    void create(EstudiantesAcudientes estudiantesAcudientes);

    void edit(EstudiantesAcudientes estudiantesAcudientes);

    void remove(EstudiantesAcudientes estudiantesAcudientes);

    EstudiantesAcudientes find(Object id);

    List<EstudiantesAcudientes> findAll();

    List<EstudiantesAcudientes> findRange(int[] range);

    int count();
    
}
