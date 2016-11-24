package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.House;
import java.util.List;

/**
 * Created by Ondro on 17-Oct-16.
 */
public interface HouseDao {

    /**
     * Save new house to DB
     *
     * @param house entity to be persisted
     */
    void create(House house);

    /**
     * Update already saved House in DB
     *
     * @param house to be updated
     * @return updated house
     */
    House update(House house);

    /**
     * Removes house from DB
     *
     * @param house to be removed
     * @throws IllegalArgumentException if the house is null.
     */
    void delete(House house);

    /**
     * search for House by its specific id
     *
     * @param id of house to be look for
     * @return specific house
     */
    House getById(Long id);

    /**
     * gives you all houses stored in DB
     *
     * @return all houses
     */
    List<House> getAll();

    /**
     * find specific house by it's name
     *
     * @param name given name
     * @return specific house, is found
     */
    House getByName(String name);
}
