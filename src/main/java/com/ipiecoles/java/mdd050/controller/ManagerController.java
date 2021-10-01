package com.ipiecoles.java.mdd050.controller;


import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private TechnicienRepository technicienRepository;
    //GET /managers/532/equipe/576/remove
    // 532 = id manager
    // 576 = id technicien
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{idTechnicien}/remove"
    )
    public void removeTechnicienFromEquipe(
            @PathVariable(value = "idManager") Long idManager,
            @PathVariable(value = "idTechnicien") Long idTechnicien
    ){
        //Récupérer le technicien à partir de son id
        Optional<Technicien> technicienOptional = technicienRepository.findById(idTechnicien);
        if(technicienOptional.isPresent()){
            //Supprimer le manager_id de mon technicien
            Technicien technicien = technicienOptional.get();
            technicien.setManager(null);//=> passer le manager_id du technicien a null
            //Faire persister la suppression du lien entre le technicien et son manager
            technicienRepository.save(technicien);
        }
    }

    //GET /managers/532/equipe/576/add
    // 532 = id manager
    // T00110 = matricule technicien

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{idManager}/equipe/{matriculeTechnicien}/add"
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ajoutTechnicienEquipe(
            @PathVariable(value = "idManager") Long idManager,
            @PathVariable(value = "matriculeTechnicien") String matricule
    ){
        //Récupère le technicien en fonction de son matricule
        Technicien technicien = technicienRepository.findByMatricule(matricule);
        //Récupère le manager à partir de son id
        Manager manager = managerRepository.findById(idManager).get();
        //Remplir la colonne manager_id du technicien
        technicien.setManager(manager);
        technicienRepository.save(technicien);
    }
}
