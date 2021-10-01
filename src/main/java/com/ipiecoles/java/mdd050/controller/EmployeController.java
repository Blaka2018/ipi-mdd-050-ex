package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {

    @Autowired
    //1 exercice = 1 méthode à développer
    private EmployeRepository employeRepository;

    //GET /employes/count => Nombre d'employés
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/count"
    )
    public Long countEmploye(){
        return employeRepository.count();
    }

    //GET /employes/id => employé d'id
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}"
    )
    public Optional<Employe> getEmployeById(
            @PathVariable(value = "id") Long id
    ){
        return employeRepository.findById(id);
    }
   //Recherche de matricule
   @RequestMapping(
           method = RequestMethod.GET,
           produces = MediaType.APPLICATION_JSON_VALUE,
           value = "",
           params = "matricule"
   )
   public Employe findEmployeByMatricule(
           @RequestParam(value = "matricule") String matricule){
       return employeRepository.findByMatricule(matricule);
   }


    //Affichage paginé des employés
    // GET /employes
   //request params page, size, sortProperty, sortDirection
  //=> Type de ces paramètres
  //=> Essayer d'écrire cette méthode de contrôleur et d'appeler
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = ""
    )
    public Page<Employe> afficheEmployes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "matricule") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection
    ){
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, sortProperty);
        return employeRepository.findAll(pageRequest);
    }


    // pour récupérer le corps de la requête du client, utiliser @RequestBody


    //pensez aux produces/consumes


    //réfléchissez au type de retour de la fonction


    //=> void dans un premier temps


    //tester en remplissant le formulaire et en cliquant sur enregistrer

    //=> Postman

    //redémarrez votre application pour tester

    //Créer la méthode Controller permettant la création d'un employé
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = ""
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employe createEmploye(@RequestBody Employe employe){
        return employeRepository.save(employe);
    }


    //PUT /employes/id => Enregistrer les modifications effectuées sur l'employé d'id
    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/{id}"
    )
    public Employe updateEmploye(
            @PathVariable(value = "id") Long id,
            @RequestBody Employe employe
    ){
        return employeRepository.save(employe);
    }


    //DELETE /employes/id => Enregistrer les Suppression effectuées sur l'employé d'id
    //DELETE /employes/id => Supprime l'employé d'id id
    //DELETE /employes/id => Supprime l'employé d'id id
    @RequestMapping(
            method = RequestMethod.DELETE,

            value = "/{id}"
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable(value = "id") Long id){
        employeRepository.deleteById(id);
    }





}
