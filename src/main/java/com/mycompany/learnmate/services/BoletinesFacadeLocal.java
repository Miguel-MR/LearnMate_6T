/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Boletines;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface BoletinesFacadeLocal {

    void create(Boletines boletines);

    void edit(Boletines boletines);

    void remove(Boletines boletines);

    Boletines find(Object id);

    List<Boletines> findAll();

    List<Boletines> findRange(int[] range);

    int count();
    
}
