/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.EstadoUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface EstadoUsuarioFacadeLocal {

    void create(EstadoUsuario estadoUsuario);

    void edit(EstadoUsuario estadoUsuario);

    void remove(EstadoUsuario estadoUsuario);

    EstadoUsuario find(Object id);

    List<EstadoUsuario> findAll();

    List<EstadoUsuario> findRange(int[] range);

    int count();
    
    // 🚨 ¡AÑADE ESTA LÍNEA AQUÍ! 🚨
    EstadoUsuario findByNombreEstado(String nombreEstado);
    
}