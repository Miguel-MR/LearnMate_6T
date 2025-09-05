/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Tareas;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface TareasFacadeLocal {

    void create(Tareas tareas);

    void edit(Tareas tareas);

    void remove(Tareas tareas);

    Tareas find(Object id);

    List<Tareas> findAll();

    List<Tareas> findRange(int[] range);

    int count();
    
    Usuarios iniciarSesion(String nombreusuario, String contrasenna);
    
}
