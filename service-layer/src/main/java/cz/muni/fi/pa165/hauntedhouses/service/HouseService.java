package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.House;

import java.util.List;

/**
 * Created by Ondro on 08-Nov-16.
 */
public interface HouseService {

    /**
     * Creates new house
     *
     * @param house to be created
     */
    void create(House house);

    /**
     * Updates house
     *
     * @param house entity to be updated
     * @return updated activity report entity
     */
    House update(House house);

    /**
     * Returns the House entity attached to the given id.
     *
     * @param id id of house entity to be returned
     * @return the house entity with given id
     */
    House findById(Long id);

    //TODO some special service calls - with proper arguments

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
     */
    void remove(House house);

}
