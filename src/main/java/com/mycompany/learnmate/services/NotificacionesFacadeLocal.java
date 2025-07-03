/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Notificaciones;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface NotificacionesFacadeLocal {

    void create(Notificaciones notificaciones);

    void edit(Notificaciones notificaciones);

    void remove(Notificaciones notificaciones);

    Notificaciones find(Object id);

    List<Notificaciones> findAll();

    List<Notificaciones> findRange(int[] range);

    int count();
    
}
