package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
@Transactional
public class CursedObjectFacadeImpl implements CursedObjectFacade {
    @Inject
    private CursedObjectService cursedObjectService;
    
    @Inject
    private HouseService houseService;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createCursedObject(CursedObjectCreateDTO cursedObjectCreateDTO) {
        if(cursedObjectCreateDTO == null) {
            throw new IllegalArgumentException("CursedObjectCreateDTO is null.");
        }
        
        CursedObject cursedObject = beanMappingService.mapTo(cursedObjectCreateDTO, CursedObject.class);
        
        // Map associated entity
        House house = houseService.getById(cursedObjectCreateDTO.getHouseId());
        if(house != null) {
            cursedObject.setHouse(house);
        }
        
        cursedObjectService.create(cursedObject);
        return cursedObject.getId();
    }
    
    @Override
    public void updateCursedObject(CursedObjectDTO cursedObjectDTO) {
        if(cursedObjectDTO == null) {
            throw new IllegalArgumentException("CursedObjectDTO is null.");
        }
        
        CursedObject cursedObject = beanMappingService.mapTo(cursedObjectDTO, CursedObject.class);
        
        // Map associated entity
        House house = houseService.getById(cursedObjectDTO.getHouseId());
        if(house != null) {
            cursedObject.setHouse(house);
        }
        
        cursedObjectService.update(cursedObject);
    }
    
    @Override
    public void deleteCursedObject(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        
        CursedObject cursedObject = new CursedObject();
        cursedObject.setId(id);
        
        cursedObjectService.delete(cursedObject);
    }
    
    @Override
    public CursedObjectDTO getCursedObjectWithId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        
        CursedObject cursedObject = cursedObjectService.getById(id);
        CursedObjectDTO cursedObjectDTO = beanMappingService.mapTo(cursedObject, CursedObjectDTO.class);
        
        // Map associated entity
        if(cursedObject != null && cursedObject.getHouse() != null) {
            cursedObjectDTO.setHouseId(cursedObject.getHouse().getId());
        }
        
        return cursedObjectDTO;
    }
    
    @Override
    public List<CursedObjectDTO> getAllCursedObjects() {
        List<CursedObject> cursedObjects = cursedObjectService.getAll();
        List<CursedObjectDTO> cursedObjectDTOs = new ArrayList<>();
        
        for(CursedObject cursedObject : cursedObjects) {
            CursedObjectDTO cursedObjectDTO = beanMappingService.mapTo(cursedObject, CursedObjectDTO.class);
            
            // Map associated entity
            if(cursedObject.getHouse() != null) {
                cursedObjectDTO.setHouseId(cursedObject.getHouse().getId());
            }
            
            cursedObjectDTOs.add(cursedObjectDTO);
        }
        return cursedObjectDTOs;
    }
    
    @Override
    public void massIncreaseMonsterAttractionFactor(MonsterAttractionFactor treshold) {
        if(treshold == null) {
            throw new IllegalArgumentException("Treshold is null.");
        }
        
        cursedObjectService.massIncreaseMonsterAttractionFactor(treshold);
    }
}
