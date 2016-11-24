package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.House;

import java.util.List;

/**
 * Business logic for House entity
 *
 * Created by Ondro on 08-Nov-16.
 */
public interface HouseService {

    /**
     * Creates new house
     *
     * @param house to be created
     * @throws IllegalArgumentException if house is null
     */
    void create(House house);

    /**
     * Updates house
     *
     * @param house entity to be updated
     * @return updated activity report entity
     * @throws IllegalArgumentException if house is null
     */
    House update(House house);

    /**
     * Returns the House entity attached to the given id.
     *
     * @param id id of house entity to be returned
     * @return the house entity with given id
     * @throws IllegalArgumentException if id is null
     */
    House findById(Long id);

    /**
     * Returns the House entity with given name.
     *
     * @param name of house entity to be returned
     * @return the house entity with given name
     * @throws IllegalArgumentException if name is null
     */
    House findByName(String name);

    /**
     * Returns all house entities.
     *
     * @return all houses
     */
    List<House> findAll();

    /**
     * Removes the house entity from persistence context.
     *
     * @param house house to be removed
     * @throws IllegalArgumentException if house is null
     */
    void remove(House house);

    /**
     * Destroys all monsters and cursed objects in the given house.
     * @param house House to be purged.
     * @throws IllegalArgumentException if the house is null.
     * @throws DataManipulationException if house is not in DB
     */
    void purge(House house);
}
