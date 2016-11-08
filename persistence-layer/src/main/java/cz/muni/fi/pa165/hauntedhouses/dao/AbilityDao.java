/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import java.util.List;

/**
 *
 * @author Kristýna Loukotová
 * @version 21.10.2016
 */
public interface AbilityDao {
    
    /**
     * Create an ability in the database.
     * @param ability Ability to be created.
     */
    void create(Ability ability);
    
    /**
     * Update the ability in the database.
     * @param ability Ability to be updated.
     */
    Ability update(Ability ability);
    
    /**
     * Deletes the ability from the database.
     * @param ability Ability to be deleted.
     */
    void delete(Ability ability);
    
    /**
     * Finds the specified ability in the database.
     * @param id ID of the ability.
     * @return Ability with specified ID. Null if the entity hasn't been found.
     */
    Ability findById(Long id);
    
    /**
     * Finds the specified ability in the database.
     * @param name Name of the ability.
     * @return Ability with specified name. Null if the entity hasn't been found.
     */
    Ability findByName(String name);
    
    /**
     * Finds all abilities in the database.
     * @return List of all abilities from the database. Null if no entity has been found.
     */
    List<Ability> findAll();
}
