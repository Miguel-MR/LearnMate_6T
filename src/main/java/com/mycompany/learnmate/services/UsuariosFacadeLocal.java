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

    void create(Usuarios usuario); // Renombré 'nombreusuario' a 'usuario' para mayor claridad

    void edit(Usuarios usuario);

    void remove(Usuarios usuario);

    Usuarios find(Object id);

    List<Usuarios> findAll();

    List<Usuarios> findRange(int[] range);

    int count();

    public Usuarios iniciarSesion(String nombreusuario, String contrasenna);
    
    public List<Usuarios> findByPersonaId(Personas persona);
    
    // 🔑 MÉTODO AÑADIDO: Fuerza la persistencia inmediata en una nueva transacción.
    public Usuarios createAndFlush(Usuarios usuario);
    
}