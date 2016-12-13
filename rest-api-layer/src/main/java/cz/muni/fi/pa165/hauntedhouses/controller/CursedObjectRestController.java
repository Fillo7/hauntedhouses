/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotModified;
import cz.muni.fi.pa165.hauntedhouses.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.CursedObjectFacade;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Marek Janco
 */

@RestController
@RequestMapping("/cursedObjects")
public class CursedObjectRestController {

    @Inject
    private CursedObjectFacade cursedObjectFacade;

    /**
     * Method creates CursedObject
     * 
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"default_name","description":"default_description","monsterAttractionFactor":"LOW","houseId":"1"}' http://localhost:8080/pa165/rest/cursedObjects
     *
     * 
     * @param createDto
     * @return created CursedObject
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CursedObjectDTO createCursedObject(@RequestBody CursedObjectCreateDTO createDto) {
        try {
            Long id = cursedObjectFacade.createCursedObject(createDto);
            return cursedObjectFacade.getCursedObjectWithId(id);
        } catch (DataManipulationException ex) {
            throw new UnprocessableEntityException("while creating CursedObject DataManipulationException was thrown", ex);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("", ex);
        }
    }

    /**
     * Updates already created CursedObject
     * 
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"new_name"}' http://localhost:8080/pa165/rest/cursedObjects/1
     * 
     * @param cursedObjectDTO contains new values of attributes
     * @param id of cursedObject in db
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void updateCursedObject(@RequestBody CursedObjectDTO cursedObjectDTO, @PathVariable("id") long id) {
        CursedObjectDTO existing = null;
        try {
            existing = cursedObjectFacade.getCursedObjectWithId(id);
        } catch (NoEntityException ex) {
            throw new UnprocessableEntityException("CursedObject id " + id + " doesnt exist", ex);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("CursedObject with id " + id + " doesnt exist", ex);
        }

        CursedObjectDTO toBeUpdated = updateDTO(cursedObjectDTO, existing);

        try {
            cursedObjectFacade.updateCursedObject(toBeUpdated);
        } catch (Exception ex) {
            throw new RequestedResourceNotModified(ex);
        }
    }

    /**
     * Deletes CursedObject
     * 
     * e.g. curl -i -X DELETE http://localhost:8080/pa165/rest/cursedObjects/1
     * 
     * @param id of CursedObject that should be deleted
     */
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public final void deleteCursedObject(@PathVariable("id") long id) {
        try {
            cursedObjectFacade.deleteCursedObject(id);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("CursedObject with if " + id + " cannot be deleted", ex);
        }
    }

    /**
     * Finds CursedObject with given id
     * 
     * e.g. curl -i -X GET http://localhost:8080/pa165/rest/cursedObjects/1
     * 
     * @param id
     * @return CursedObject with given id
     */
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public CursedObjectDTO getCursedObjectById(@PathVariable("id") long id) {
        try {
            return cursedObjectFacade.getCursedObjectWithId(id);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("cannot find CursedObject with id "+id, ex);
        }
    }

    /**
     * Finds all CursedObjects
     * 
     * e.g. curl -i -X GET http://localhost:8080/pa165/rest/cursedObjects
     * 
     * @return list of all CursedObjects
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<CursedObjectDTO> getAllCursedObjects() {
        try {
            return Collections.unmodifiableList( cursedObjectFacade.getAllCursedObjects() );
        } catch (Exception ex) {
            throw new UnprocessableEntityException("problem appeared in finding all CursedObjects", ex);
        }
    }

    private CursedObjectDTO updateDTO(CursedObjectDTO cursedObjectDTO, CursedObjectDTO existing) {
        CursedObjectDTO result = new CursedObjectDTO();
        result.setId(existing.getId());

        if (cursedObjectDTO.getName() == null) {
            cursedObjectDTO.setName(existing.getName());
        }
        if (cursedObjectDTO.getDescription() == null) {
            cursedObjectDTO.setDescription(existing.getDescription());
        }
        if (cursedObjectDTO.getHouse() == null) {
            cursedObjectDTO.setHouse(existing.getHouse());
        }
        if (cursedObjectDTO.getMonsterAttractionFactor() == null) {
            cursedObjectDTO.setMonsterAttractionFactor(existing.getMonsterAttractionFactor());
        }

        return result;
    }

}
