package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import java.util.List;

/**
 * @author Filip Petrovic (422334)
 */
public interface CursedObjectDao {
    /**
     * Adds new cursed object.
     * @param cursedObject Cursed object to be added.
     * @throws IllegalArgumentException when cursedObject is null or id is already set.
     */
    void create(CursedObject cursedObject);

    /**
     * Updates existing cursed object.
     * @param cursedObject Cursed object to be updated.
     * @return updated CursedObject
     * @throws IllegalArgumentException when cursedObject is null.
     */
    CursedObject update(CursedObject cursedObject);

    /**
     * Deletes existing cursed object.
     * @param cursedObject Cursed object to be deleted.
     * @throws IllegalArgumentException when cursed object is null.
     */
    void delete(CursedObject cursedObject);

    /**
     * Returns existing cursed object with given id.
     * @param id Id of a cursed object to be returned.
     * @return Existing cursed object with given id.
     * @throws IllegalArgumentException when id is null.
     */
    CursedObject findById(Long id);

    /**
     * Returns list of all cursed objects.
     * @return List of all cursed objects.
     */
    List<CursedObject> findAll();
}
