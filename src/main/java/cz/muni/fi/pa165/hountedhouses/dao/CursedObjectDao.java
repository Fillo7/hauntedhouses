package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.entity.CursedObject;
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
    void addCursedObject(CursedObject cursedObject);
    
    /**
     * Updates existing cursed object.
     * @param cursedObject Cursed object to be updated.
     * @throws IllegalArgumentException when cursedObject is null or id is null.
     */
    void updateCursedObject(CursedObject cursedObject);
    
    /**
     * Deletes existing cursed object.
     * @param id Id of a cursed object to be deleted.
     * @throws IllegalArgumentException when id is null.
     */
    void deleteCursedObject(Long id);
    
    /**
     * Returns existing cursed object with given id.
     * @param id Id of a cursed object to be returned.
     * @return Existing cursed object with given id.
     * @throws IllegalArgumentException when id is null.
     */
    CursedObject getCursedObject(Long id);
    
    /**
     * Returns list of all cursed objects.
     * @return List of all cursed objects.
     */
    List<CursedObject> getAllCursedObjects();
}
