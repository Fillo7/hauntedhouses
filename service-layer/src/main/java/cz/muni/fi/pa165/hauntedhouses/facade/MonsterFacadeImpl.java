package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Marek Janco
 */
@Service
@Transactional
public class MonsterFacadeImpl implements MonsterFacade {

    @Inject
    private MonsterService monsterService;

    @Inject
    private AbilityService abilityService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createMonster(MonsterCreateDTO monsterCreateDTO) {
        if (monsterCreateDTO == null) {
            throw new IllegalArgumentException("monster cannot be null");
        }

        Monster monsterCreate = beanMappingService.mapTo(monsterCreateDTO, Monster.class);
        monsterService.create(monsterCreate);
        return monsterCreate.getId();
    }

    @Override
    public void updateMonster(MonsterDTO monsterDTO) {
        if (monsterDTO == null) {
            throw new IllegalArgumentException("monster cannot be null");
        }
        if (monsterDTO.getId() == null) {
            throw new IllegalArgumentException("monsterDto id is null");
        }

        Monster monster = beanMappingService.mapTo(monsterDTO, Monster.class);
        if (monsterService.getById(monster.getId()) == null) {
            throw new NoEntityException("updating not existing monster");
        }

        monsterService.update(monster);
    }

    @Override
    public void deleteMonster(Long id) {
        if(id == null){
            throw new IllegalArgumentException("monsterId cannot be null");
        }

        Monster monster = monsterService.getById(id);
        if(monster == null){
            throw new NoEntityException("monster with id=" + id + " does not exist, cannot remove");
        }

        monsterService.delete(monster);
    }

    @Override
    public MonsterDTO getMonsterById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("monster id cannot be null");
        }

        Monster monster = monsterService.getById(id);
        if(monster == null){
            throw new NoEntityException("monster with id=" + id + " does not exist");
        }

        return beanMappingService.mapTo(monster, MonsterDTO.class);
    }

    @Override
    public List<MonsterDTO> getAllMonsters() {
        return beanMappingService.mapTo(monsterService.getAll(), MonsterDTO.class);
    }

    @Override
    public void moveToAnotherHouse(MonsterDTO monster, HouseDTO house) {
        if (monster == null) {
            throw new IllegalArgumentException("Null monster cannot be moved.");
        }
        if (house == null) {
            throw new IllegalArgumentException("Monster cannot be moved to null house.");
        }
        Monster monsterEntity = beanMappingService.mapTo(monster, Monster.class);
        House houseEntity = beanMappingService.mapTo(house, House.class);

        monsterService.moveToAnotherHouse(monsterEntity, houseEntity);
    }

}
