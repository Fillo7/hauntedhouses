package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import java.util.List;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
public interface AbilityService {
    /**
     * Create new ability.
     * @param ability Ability to be created.
     */
    void create(Ability ability);

    /**
     * Update existing ability.
     * @param ability Ability to be updated.
     * @return Updated ability.
     */
    Ability update(Ability ability);

    /**
     * Delete existing ability.
     * @param ability Ability to be deleted.
     */
    void remove(Ability ability);

    /**
     * Returns existing ability with given ID.
     * @param id ID of the wanted ability.
     * @return Ability with given ID.
     */
    Ability findById(Long id);

    /**
     * Returns existing ability with given name.
     * @param name Name of the wanted ability.
     * @return Ability with given name.
     */
    Ability findByName(String name);

    /**
     * Returns all abilities.
     * @return List of all abilities.
     */
    List<Ability> findAll();
}
