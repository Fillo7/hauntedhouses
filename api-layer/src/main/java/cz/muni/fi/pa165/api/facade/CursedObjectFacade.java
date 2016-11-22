package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.api.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;

import java.util.List;

/**
 * @author Filip Petrovic (422334)
 */
public interface CursedObjectFacade {
    /**
     * Creates new cursed object.
     * @param cursedObjectCreateDTO Cursed object to be created
     * @return Id of the created cursed object
     */
    Long createCursedObject(CursedObjectCreateDTO cursedObjectCreateDTO);
    
    /**
     * Updates existing cursed object.
     * @param cursedObjectDTO Cursed object to be updated
     */
    void updateCursedObject(CursedObjectDTO cursedObjectDTO);
    
    /**
     * Deletes existing cursed object.
     * @param id Id of cursed object to be deleted
     */
    void deleteCursedObject(Long id);
    
    /**
     * Returns cursed object with given id.
     * @param id Id of a cursed object to be returned
     * @return Cursed object with given id
     */
    CursedObjectDTO getCursedObjectWithId(Long id);
    
    /**
     * Returns all cursed objects.
     * @return All cursed objects
     */
    List<CursedObjectDTO> getAllCursedObjects();
    
    /**
     * Increases monster attraction factor of specified cursed objects by one (up to insane).
     * @param treshold Increase will only be applied to cursed objects with attraction factor <= treshold
     */
    void massIncreaseMonsterAttractionFactor(MonsterAttractionFactor treshold);
}
