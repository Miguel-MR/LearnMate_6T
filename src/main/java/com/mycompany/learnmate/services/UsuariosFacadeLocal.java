/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Personas;
import com.mycompany.learnmate.entities.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author castr
 */
@Local
public interface UsuariosFacadeLocal {

    void create(Usuarios usuarios);

    void edit(Usuarios usuarios);

    void remove(Usuarios usuarios);

    Usuarios find(Object id);

    List<Usuarios> findAll();

    List<Usuarios> findRange(int[] range);

    int count();

    public Usuarios iniciarSesion(String usuario, String contrasenna);
    
    public List<Usuarios> findByPersonaId(Personas persona);
    
}
