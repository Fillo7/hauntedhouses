package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotFound;
import cz.muni.fi.pa165.hauntedhouses.exceptions.UnprocessableEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Petrovic (422334)
 */
@RestController
@RequestMapping(Uri.HOUSES)
public class HouseRestController {
    @Inject
    HouseFacade houseFacade;
    
    /**
     * Creates a new house by POST method.
     * Command: curl -X POST -i -H "Content-Type: application/json" --data 
     * '{"name":"string","address":"string","monsterIds":"","cursedObjectIds":""}' 
     * http://localhost:8080/pa165/rest/houses/create
     * @param house HouseCreateDTO with required fields for creation
     * @return newly created house
     * @throws UnprocessableEntityException when house creation failed.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO createHouse(@RequestBody HouseCreateDTO house) throws UnprocessableEntityException {
        try {
            Long id = houseFacade.createHouse(house);
            return houseFacade.getHouseById(id);
        } catch (Exception ex) {
            throw new UnprocessableEntityException("Parameters were specified incorrectly or requested resource already exists.", ex);
        }
    }
    
    /**
     * Updates given house by PUT method.
     * Command: curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"string","address":"string","monsterIds":"1","cursedObjectIds":"1"}'
     * http://localhost:8080/pa165/rest/houses/{id}
     * @param id identifier of a house
     * @param house updated house
     * @return updated house
     * @throws RequestedResourceNotFound when given house is not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO updateHouse(@PathVariable("id") long id, @RequestBody HouseDTO house) throws RequestedResourceNotFound {
        try {
            house.setId(id);
            houseFacade.updateHouse(house);
            return houseFacade.getHouseById(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFound("House with given id doesn't exist: " + id, ex);
        }
    }
    
    /**
     * Returns house with given id.
     * Command: curl -i -X GET
     * http://localhost:8080/pa165/rest/houses/id/{id}
     * @param id identifier of a house
     * @return house with given id
     * @throws RequestedResourceNotFound when given house is not found.
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO getHouseById(@PathVariable("id") long id) throws RequestedResourceNotFound {
        try {
            return houseFacade.getHouseById(id);
        } catch (NoEntityException ex) {
            throw new RequestedResourceNotFound("House with given id doesn't exist: " + id, ex);
        }
    }
    
    /**
     * Returns house with given name.
     * Command: curl -i -X GET
     * http://localhost:8080/pa165/rest/houses/name/{name}
     * @param name name of a house
     * @return house with given name
     * @throws RequestedResourceNotFound when given house is not found.
     */
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO getHouseByName(@PathVariable("name") String name) throws RequestedResourceNotFound {
        try {
            return houseFacade.getHouseByName(name);
        } catch (NoEntityException ex) {
            throw new RequestedResourceNotFound("House with given name doesn't exist: " + name, ex);
        }
    }
    
    /**
     * Deletes house with given id.
     * Command: curl -i -X DELETE
     * http://localhost:8080/pa165/rest/houses/{id}
     * @param id identifier of a house
     * @throws RequestedResourceNotFound when given house is not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteHouse(@PathVariable("id") long id) throws RequestedResourceNotFound {
        try {
            houseFacade.deleteHouse(id);
        } catch (Exception ex) {
            throw new RequestedResourceNotFound("House with given id doesn't exist: " + id, ex);
        }
    }
    
    /**
     * Returns list of houses.
     * Command: curl -i -X GET
     * http://localhost:8080/pa165/rest/houses
     * @return list of houses
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<HouseDTO> getAllHouses() {
        return houseFacade.getAllHouses();
    }
    
    /**
     * Purges all monsters and cursed objects in given house.
     * http://localhost:8080/pa165/rest/houses/{id}
     * @param id identifier of a house
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void purge(@PathVariable("id") long id) {
        HouseDTO house = houseFacade.getHouseById(id);
        houseFacade.purge(house);
    }
}
