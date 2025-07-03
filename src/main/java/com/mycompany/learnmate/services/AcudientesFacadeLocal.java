/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Acudientes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface AcudientesFacadeLocal {

    void create(Acudientes acudientes);

    void edit(Acudientes acudientes);

    void remove(Acudientes acudientes);

    Acudientes find(Object id);

    List<Acudientes> findAll();

    List<Acudientes> findRange(int[] range);

    int count();
    
}
