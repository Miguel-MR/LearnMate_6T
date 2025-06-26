/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.EntregasTareas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface EntregasTareasFacadeLocal {

    void create(EntregasTareas entregasTareas);

    void edit(EntregasTareas entregasTareas);

    void remove(EntregasTareas entregasTareas);

    EntregasTareas find(Object id);

    List<EntregasTareas> findAll();

    List<EntregasTareas> findRange(int[] range);

    int count();
    
}
