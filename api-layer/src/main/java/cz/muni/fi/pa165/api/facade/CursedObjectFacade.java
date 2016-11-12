package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.api.dto.CursedObjectDTO;
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
     * @param cursedObjectId Id of cursed object to be deleted
     */
    void deleteCursedObject(Long cursedObjectId);
    
    /**
     * Returns cursed object with given id.
     * @param cursedObjectId Id of a cursed object to be returned
     * @return Cursed object with given id
     */
    CursedObjectDTO getCursedObjectWithId(Long cursedObjectId);
    
    /**
     * Returns all cursed objects.
     * @return All cursed objects
     */
    List<CursedObjectDTO> getAllCursedObjects();
}
