/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Grados;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface GradosFacadeLocal {

    void create(Grados grados);

    void edit(Grados grados);

    void remove(Grados grados);

    Grados find(Object id);

    List<Grados> findAll();

    List<Grados> findRange(int[] range);

    int count();
    
}
