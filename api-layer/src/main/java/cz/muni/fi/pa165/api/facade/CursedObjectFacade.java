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
     * @param cursedObject Cursed object to be created
     * @return Id of the created cursed object
     */
    public Long createCursedObject(CursedObjectCreateDTO cursedObject);
    
    /**
     * Updates existing cursed object.
     * @param cursedObject Cursed object to be updated
     */
    void updateCursedObject(CursedObjectDTO cursedObject);
    
    /**
     * Returns cursed object with given id.
     * @param id Id of a cursed object to be returned
     * @return Cursed object with given id
     */
    public CursedObjectDTO getCursedObjectWithId(Long id);
    
    /**
     * Returns all cursed objects.
     * @return All cursed objects
     */
    public List<CursedObjectDTO> getAllCursedObjects();
}
