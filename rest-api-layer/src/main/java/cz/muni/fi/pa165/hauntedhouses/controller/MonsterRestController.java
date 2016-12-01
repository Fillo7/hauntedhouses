package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotFound;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<MonsterDTO> getAllMonsters(){
        List<MonsterDTO> result = new ArrayList<>();
        result.addAll(monsterFacade.getAllMonsters());
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final MonsterDTO getMonsterById(@PathVariable("id") long id){
        try {
            return monsterFacade.getMonsterById(id);
        } catch (NoEntityException e) {
            throw new RequestedResourceNotFound(e);
        }
    }

}
