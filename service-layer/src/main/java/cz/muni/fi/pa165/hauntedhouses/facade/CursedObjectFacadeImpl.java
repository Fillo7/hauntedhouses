package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.api.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.api.dto.CursedObjectDTO;
import cz.muni.fi.pa165.api.facade.CursedObjectFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
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
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createCursedObject(CursedObjectCreateDTO cursedObjectCreateDTO) {
        CursedObject cursedObject = new CursedObject();
        
        cursedObject.setName(cursedObjectCreateDTO.getName());
        cursedObject.setDescription(cursedObjectCreateDTO.getDescription());
        cursedObject.setMonsterAttractionFactor(beanMappingService.mapTo(cursedObjectCreateDTO.getMonsterAttractionFactor(), MonsterAttractionFactor.class));
        cursedObject.setHouse(beanMappingService.mapTo(cursedObjectCreateDTO.getHouse(), House.class));
        cursedObjectService.create(cursedObject);
        
        return cursedObject.getId();
    }
    
    @Override
    public void updateCursedObject(CursedObjectDTO cursedObjectDTO) {
        CursedObject cursedObject = new CursedObject();
        
        cursedObject.setId(cursedObjectDTO.getId());
        cursedObject.setName(cursedObjectDTO.getName());
        cursedObject.setDescription(cursedObjectDTO.getDescription());
        cursedObject.setMonsterAttractionFactor(beanMappingService.mapTo(cursedObjectDTO.getMonsterAttractionFactor(), MonsterAttractionFactor.class));
        cursedObject.setHouse(beanMappingService.mapTo(cursedObjectDTO.getHouse(), House.class));
        
        cursedObjectService.update(cursedObject);
    }
    
    @Override
    public void deleteCursedObject(Long id) {
        CursedObject cursedObject = new CursedObject();
        cursedObject.setId(id);
        
        cursedObjectService.delete(cursedObject);
    }
    
    @Override
    public CursedObjectDTO getCursedObjectWithId(Long id) {
        CursedObject cursedObject = cursedObjectService.findById(id);
        return beanMappingService.mapTo(cursedObject, CursedObjectDTO.class);
    }
    
    @Override
    public List<CursedObjectDTO> getAllCursedObjects() {
        List<CursedObject> cursedObjects = cursedObjectService.findAll();
        return beanMappingService.mapTo(cursedObjects, CursedObjectDTO.class);
    }
    
    @Override
    public void massIncreaseMonsterAttractionFactor(MonsterAttractionFactor treshold) {
        cursedObjectService.massIncreaseMonsterAttractionFactor(beanMappingService.mapTo(treshold, MonsterAttractionFactor.class));
    }
}
