/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotFound;
import cz.muni.fi.pa165.hauntedhouses.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kristyna Loukotova
 * @cersion 12.12.2016
 */
@RestController
@RequestMapping(Uri.ABILITIES)
public class AbilityRestController {

    @Inject
    AbilityFacade abilityFacade;

    /**
     * Creates new ability via POST method.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"defaultName","description":"defaultDescription","monsterIds":[]}' http://localhost:8080/pa165/rest/abilities/create
     *
     * @param ability AbilityCreateDTO to create an ability from
     * @return Newly created ability
     * @throws UnprocessableEntityException if the ability creation failed
     */
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AbilityDTO createAbility(@RequestBody AbilityCreateDTO ability) throws UnprocessableEntityException {
        try {
            Long id = abilityFacade.createAbility(ability);
            return abilityFacade.getAbilityById(id);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("Parameters were specified incorrectly or requested resource already exists.", ex);
        }
    }

    /**
     * Updates the ability via PUT method.
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"defaultName","description":"defaultDescription","monsterIds":[1]}' http://localhost:8080/pa165/rest/abilities?update={id}
     *
     * @param id Ability identifier
     * @param ability Ability to be updated
     * @return Updated ability
     * @throws RequestedResourceNotFound If the ability wasn't found in the database
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final AbilityDTO updateHouse(@RequestParam(value="update") long id, @RequestBody AbilityDTO ability) throws RequestedResourceNotFound {
        try {
            ability.setId(id);
            abilityFacade.updateAbility(ability);
            return abilityFacade.getAbilityById(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFound("Ability with given id doesn't exist: " + id, ex);
        }
    }

    /**
     * Deletes ability with given id.
     *
     * curl -i -X DELETE http://localhost:8080/pa165/rest/abilities?delete={id}
     *
     * @param id Ability identifier
     * @throws RequestedResourceNotFound if the ability wasn't found in the database
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteHouse(@RequestParam(value="delete") long id) throws RequestedResourceNotFound {
        try {
            abilityFacade.deleteAbility(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFound("Ability with given id doesn't exist: " + id, ex);
        }
    }

    /**
     * Finds ability with given id.
     *
     * curl -i -X GET http://localhost:8080/pa165/rest/abilities/id/{id}
     * where {id} stands for numerical value of the ability identifier.
     *
     * @param id Ability identifier
     * @return Ability with given id
     * @throws RequestedResourceNotFound if the ability wasn't found in the database
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AbilityDTO getAbilityById(@PathVariable(value="id") long id) throws RequestedResourceNotFound {
        try {
            return abilityFacade.getAbilityById(id);
        } catch (NoEntityException ex) {
            throw new RequestedResourceNotFound("Ability with given id doesn't exist: " + id, ex);
        }
    }

    /**
     * Finds ability with given name.
     *
     * curl -i -X GET http://localhost:8080/pa165/rest/abilities/name/{name}
     * where {name} stands for string value of the ability name (in correct encoding).
     *
     * @param name Name of the ability
     * @return Ability with given name
     * @throws RequestedResourceNotFound if the ability wasn't found in the database
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final AbilityDTO getAbilityByName(@PathVariable(value="name") String name) throws RequestedResourceNotFound {
        try {
            return abilityFacade.getAbilityByName(name);
        } catch (NoEntityException ex) {
            throw new RequestedResourceNotFound("Ability with given name doesn't exist: " + name, ex);
        }
    }

    /**
     * Returns list of abilities in the database.
     *
     * curl -i -X GET http://localhost:8080/pa165/rest/abilities
     *
     * @return List of all the abilities
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<AbilityDTO> getAllAbilities() {
        return Collections.unmodifiableList(abilityFacade.getAllAbilities());
    }
}
