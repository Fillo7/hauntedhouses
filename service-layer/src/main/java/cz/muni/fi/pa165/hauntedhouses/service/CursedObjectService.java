package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import java.util.List;

/**
 * @author Filip Petrovic (422334)
 */
public interface CursedObjectService {
    /**
     * Adds new cursed object.
     * @param cursedObject Cursed object to be added.
     */
    void create(CursedObject cursedObject);

    /**
     * Updates existing cursed object.
     * @param cursedObject Cursed object to be updated.
     * @return updated CursedObject
     */
    CursedObject update(CursedObject cursedObject);

    /**
     * Deletes existing cursed object.
     * @param cursedObject Cursed object to be deleted.
     */
    void delete(CursedObject cursedObject);

    /**
     * Returns existing cursed object with given id.
     * @param id Id of a cursed object to be returned.
     * @return Existing cursed object with given id.
     */
    CursedObject findById(Long id);

    /**
     * Returns list of all cursed objects.
     * @return List of all cursed objects.
     */
    List<CursedObject> findAll();
}
