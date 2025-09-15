package com.mycompany.learnmate.services;

import com.mycompany.learnmate.entities.Cursos;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CursosFacadeLocal {

    void create(Cursos cursos);

    void edit(Cursos cursos);

    void remove(Cursos cursos);

    Cursos find(Object id);

    List<Cursos> findAll();

    List<Cursos> findRange(int[] range);

    int count();
    
    // ✅ Método requerido por ControllerUsuario para crear Estudiantes
    Cursos findByNombre(String nombreCurso);
    
}