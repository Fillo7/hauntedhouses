package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotFound;
import cz.muni.fi.pa165.hauntedhouses.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ondrej Oravcok on 01-Dec-16.
 */
@RestController
@RequestMapping("/monsters")
public class MonsterRestController {

    @Inject
    MonsterFacade monsterFacade;

    /**
     * Gives you all Monsters in system
     *
     * e.g. curl -i -X GET http://localhost:8080/pa165/rest/monsters
     *
     * @return list of monsters in system
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MonsterDTO> getAllMonsters(){
        List<MonsterDTO> result = new ArrayList<>();
        result.addAll(monsterFacade.getAllMonsters());
        return result;
    }

    /**
     * Gives you specific monster, if exists
     *
     * e.g. curl -i -X GET http://loclahost:8080/pa165/rest/monsters/6
     *
     * @param id monster id
     * @return DTO of that Monster
     * @throws RequestedResourceNotFound if there is no Monster with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDTO getMonsterById(@PathVariable("id") long id) throws RequestedResourceNotFound {
        try {
            return monsterFacade.getMonsterById(id);
        } catch (NoEntityException e) {
            throw new RequestedResourceNotFound("Monster with id=" + id + " does not exist in system.", e);
        }
    }

    /**
     * Delete specific monster, if exists
     *
     * e.g. curl -i -X DELETE http://loclahost:8080/pa165/rest/monsters/6
     *
     * @param id monster id
     * @throws RequestedResourceNotFound if there is no Monster with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteMonster(@PathVariable("id") long id) throws RequestedResourceNotFound {
        try {
            monsterFacade.deleteMonster(id);
        } catch (NoEntityException | IllegalArgumentException e) {
            throw new RequestedResourceNotFound("Cannot delete. Monster with id=" + id + " does not exist in system.", e);
        }
    }

    /**
     * Create a new monster by POST method.
     *
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Homer Simpson","description":"Massive big yellow mmonster","hauntedIntervalStart":"6:15","hauntedIntervalEnd":"16:50","houseId":"1","abilityIds":""}' http://localhost:8080/pa165/rest/monsters
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Homer Simpson","description":"Massive big yellow mmonster","hauntedIntervalStart":[6,15],"hauntedIntervalEnd":[16,50],"houseId":"1","abilityIds":[]}' http://localhost:8080/pa165/rest/monsters
     *
     * @param monster MonsterCreateDTO with required fields for creation
     * @return the newly created DTO of monster
     * @throws UnprocessableEntityException if monster can not be created because validation failures or Monster already exists
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDTO createMonster(@RequestBody MonsterCreateDTO monster) throws UnprocessableEntityException {
        try{
            Long id = monsterFacade.createMonster(monster);
            return monsterFacade.getMonsterById(id);
        } catch (DataManipulationException e) {
            throw new UnprocessableEntityException("DataManipulationException thrown while creating Monster.", e);
        } catch (Exception e){
            throw new UnprocessableEntityException("Requested resource already exists.", e);
        }
    }

}
