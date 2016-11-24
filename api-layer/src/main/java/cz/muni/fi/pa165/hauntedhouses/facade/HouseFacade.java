package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseUpdateDTO;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;

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
     * @param houseCreateDTO entity to be created
     * @return id of newly created house
     * @throws IllegalArgumentException if houseCreateDTO is null
     */
    Long createHouse(HouseCreateDTO houseCreateDTO);

    /**
     * Updates house
     *
     * @param houseUpdateDTO entity to be updated
     * @throws IllegalArgumentException if houseUpdateDTO is null
     * @throws NoEntityException if updating non-persisted entity
     */
    void updateHouse(HouseUpdateDTO houseUpdateDTO);

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
     * @throws NoEntityException if House with given Id does not exist
     */
    HouseDTO getHouseById(Long houseId);

    /**
     * Returns house according to given name.
     *
     * @param houseName
     * @return house identified by unique name
     * @throws IllegalArgumentException if houseName is null
     * @throws NoEntityException if House with given name does not exist
     */
    HouseDTO getHouseByName(String houseName);

    /**
     * Removes house
     *
     * @param houseId id of house to delete
     * @throws IllegalArgumentException if houseId is null
     * @throws NoEntityException if House with given Id does not exist
     */
    void deleteHouse(Long houseId);

    /**
     * Destroys all monsters and cursed objects in the given house.
     * @param house House to be purged.
     * @throws IllegalArgumentException if the house is null.
     */
    void purge(HouseDTO house);
}
