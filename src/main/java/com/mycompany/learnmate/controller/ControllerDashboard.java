package com.mycompany.learnmate.controller;

import com.mycompany.learnmate.entities.Usuarios;
import com.mycompany.learnmate.services.UsuariosFacadeLocal;
import com.mycompany.learnmate.services.GradosFacadeLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("controllerDashboard")
@ViewScoped
public class ControllerDashboard implements Serializable {

    @EJB
    private UsuariosFacadeLocal usuariosFacade;

    @EJB
    private GradosFacadeLocal gradosFacade;

    private long totalUsuarios;
    private long totalEstudiantes;
    private long totalProfesores;
    private long totalAcudientes;
    private long totalGrados;

    @PostConstruct
    public void init() {
        // total de usuarios
        totalUsuarios = usuariosFacade.count();

        // recorrer cada usuario y verificar su rol
        for (Usuarios u : usuariosFacade.findAll()) {
            if (u.getRolesUsuarioList() != null) {
                u.getRolesUsuarioList().forEach(ru -> {
                    String rol = ru.getRolId().getNombreRol(); // ⚠️ Ajusta según tu entidad Roles
                    if (rol == null) {
                        return;
                    }

                    switch (rol.toLowerCase()) {
                        case "estudiante":
                            totalEstudiantes++;
                            break;
                        case "profesor":
                            totalProfesores++;
                            break;
                        case "acudiente":
                            totalAcudientes++;
                            break;
                    }
                });
            }
        }
        
        totalGrados = gradosFacade.count();
    }

    // getters
    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public long getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public long getTotalProfesores() {
        return totalProfesores;
    }

    public long getTotalAcudientes() {
        return totalAcudientes;
    }

    public long getTotalGrados() {
        return totalGrados;
    }
}
