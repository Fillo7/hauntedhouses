package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;

/**
 *
 * @author Marek Janco
 */
public interface MonsterDao {

    /**
    * create a monster in DB
    *
    * @param monster to be created
    */
    void create(Monster monster);

    /**
    * update monster, which is saved in DB
    *
    * @param monster to be updated
    *
    * @return updated monster
    */
    Monster update(Monster monster);

    /**
    * delete monster from DB
    *
    * @param monster to be deleted
    *
    * @throws IllegalArgumentException if the monster is null
    */
    void delete(Monster monster);

    /**
    * Find monster in DB
    *
    * @param id of monster that should be found
    * @return Monster if found, null if not
    */
    Monster findById(Long id);

    /**
    * List all monsters that are in DB
    *
    * @return List of all monsters
    */
    List<Monster> findAll();

    /**
     * find specific monster by it's name
     *
     * @param name given name
     * @return specific Monster, if found
     */
    Monster findByName(String name);
}
