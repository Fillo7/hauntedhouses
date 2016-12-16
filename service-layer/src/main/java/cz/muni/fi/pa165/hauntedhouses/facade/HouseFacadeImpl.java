package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation for HouseFacade
 *
 * Created by Ondrej Oravcok on 09-Nov-16.
 */
@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    @Inject
    private HouseService houseService;
    
    @Inject
    private MonsterService monsterService;
    
    @Inject
    private CursedObjectService cursedObjectService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createHouse(HouseCreateDTO houseCreateDTO) {
        if(houseCreateDTO == null){
            throw new IllegalArgumentException("house cannot be null");
        }

        House house = beanMappingService.mapTo(houseCreateDTO, House.class);
        
        // Map associated entities
        for(Long monsterId : houseCreateDTO.getMonsterIds()) {
            Monster monster = monsterService.getById(monsterId);
            if(monster != null){
                house.addMonster(monster);
            }
        }
        
        for(Long cursedObjectId : houseCreateDTO.getCursedObjectIds()) {
            CursedObject cursedObject = cursedObjectService.getById(cursedObjectId);
            if (cursedObject != null){
                house.addCursedObject(cursedObject);
            }
        }
        
        houseService.create(house);
        return house.getId();
    }

    @Override
    public void updateHouse(HouseDTO houseDTO) {
        if(houseDTO == null){
            throw new IllegalArgumentException("house cannot be null");
        }

        House house = beanMappingService.mapTo(houseDTO, House.class);
        if(houseService.getById(house.getId()) == null){
            throw new NoEntityException("updating non existing house");
        }
        
        // Map associated entities
        for(Long monsterId : houseDTO.getMonsterIds()) {
            Monster monster = monsterService.getById(monsterId);
            if(monster != null){
                house.addMonster(monster);
            }
        }
        
        for(Long cursedObjectId : houseDTO.getCursedObjectIds()) {
            CursedObject cursedObject = cursedObjectService.getById(cursedObjectId);
            if (cursedObject != null){
                house.addCursedObject(cursedObject);
            }
        }
        
        houseService.update(house);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseService.getAll();
        List<HouseDTO> houseDTOs = new ArrayList<>();
        
        for(House house : houses) {
            HouseDTO houseDTO = beanMappingService.mapTo(house, HouseDTO.class);
            
            // Map associated entities
            if(house.getMonsters() != null) {
                for(Monster monster : house.getMonsters()) {
                    houseDTO.addMonsterId(monster.getId());
                }
            }
        
            if(house.getCursedObjects() != null) {
                for(CursedObject cursedObject : house.getCursedObjects()) {
                    houseDTO.addCursedObjectId(cursedObject.getId());
                }
            }
            
            houseDTOs.add(houseDTO);
        }
        return houseDTOs;
    }

    @Override
    public HouseDTO getHouseById(Long houseId) {
        if(houseId == null){
            throw new IllegalArgumentException("houseId cannot be null");
        }

        House house = houseService.getById(houseId);
        if(house == null){
            throw new NoEntityException("house with id=" + houseId + " does not exist");
        }
        HouseDTO houseDTO = beanMappingService.mapTo(house, HouseDTO.class);
        
        // Map associated entities
        if(house.getMonsters() != null) {
            for(Monster monster : house.getMonsters()) {
                houseDTO.addMonsterId(monster.getId());
            }
        }
        
        if(house.getCursedObjects() != null) {
            for(CursedObject cursedObject : house.getCursedObjects()) {
                houseDTO.addCursedObjectId(cursedObject.getId());
            }
        }
        
        return houseDTO;
    }

    @Override
    public HouseDTO getHouseByName(String houseName) {
        if(houseName == null){
            throw new IllegalArgumentException("houseName cannot be null");
        }

        House house = houseService.getByName(houseName);
        if(house == null){
            throw new NoEntityException("house with name=" + houseName + " does not exist");
        }
        HouseDTO houseDTO = beanMappingService.mapTo(house, HouseDTO.class);
        
        // Map associated entities
        if(house.getMonsters() != null) {
            for(Monster monster : house.getMonsters()) {
                houseDTO.addMonsterId(monster.getId());
            }
        }
        
        if(house.getCursedObjects() != null) {
            for(CursedObject cursedObject : house.getCursedObjects()) {
                houseDTO.addCursedObjectId(cursedObject.getId());
            }
        }
        
        return houseDTO;
    }

    @Override
    public void deleteHouse(Long houseId) {
        if(houseId == null){
            throw new IllegalArgumentException("houseId cannot be null");
        }

        House house = houseService.getById(houseId);
        if(house == null){
            throw new NoEntityException("house with id=" + houseId + " does not exist, cannot remove");
        }

        houseService.delete(house);
    }

    @Override
    public void purge(HouseDTO house) {
        if (house == null) {
            throw new IllegalArgumentException("House is null.");
        }

        House houseEntity = beanMappingService.mapTo(house, House.class);

        houseService.purge(houseEntity);
    }
}
