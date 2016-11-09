package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.create.HouseCreateDTO;

import java.util.List;

/**
 * Facade interface for House
 *
 * Created by Ondro on 09-Nov-16.
 */
public interface HouseFacade {

    /**
     * Creates house
     *
     * @param house entity to be created
     * @return id of newly created house
     */
    Long createHouse(HouseCreateDTO house);

    /**
     * Updates house
     *
     * @param house entity to be updated
     */
    void updateHouse(HouseDTO house);

    /**
     * Returns all houses
     *
     * @return list of all house entities
     */
    List<HouseDTO> getAllHouses();

    /**
     * Returns house according to given id.
     *
     * @param houseId
     * @return house identified by unique id
     * @throws IllegalArgumentException if houseId is null
     */
    HouseDTO getHouseById(Long houseId);

    //TODO define more specific facade methods with proper arguments

    /**
     * Removes house
     *
     * @param houseId id of house to delete
     */
    void removeHouse(Long houseId);

}
