package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterUpdateDTO;
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
    private HouseService houseService;

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

        monsterCreate.setName(monsterCreateDTO.getName());
        monsterCreate.setDescription(monsterCreateDTO.getDescription());
        monsterCreate.setHauntedIntervalStart(monsterCreateDTO.getHauntedIntervalStart());
        monsterCreate.setHauntedIntervalEnd(monsterCreateDTO.getHauntedIntervalEnd());
        monsterCreate.setHouse(getHouse(monsterCreateDTO.getHouseId()));

        Set<Long> abilities = monsterCreateDTO.getAbilitiesId();
        abilities.forEach(ability -> monsterCreate.addAbility(getAbility(ability)));

        monsterService.create(monsterCreate);
        return monsterCreate.getId();
    }

    @Override
    public void updateMonster(MonsterUpdateDTO monsterDTO) {
        if (monsterDTO == null) {
            throw new IllegalArgumentException("monster cannot be null");
        }
        if (monsterDTO.getId() == null || monsterService.findById(monsterDTO.getId()) == null) {
            throw new IllegalArgumentException("monster is not in DB, cannot be updated");
        }

        Monster monster = beanMappingService.mapTo(monsterDTO, Monster.class);
        if (monsterService.findById(monster.getId()) == null) {
            throw new NoEntityException("updating not existing monster");
        }

        monsterService.update(monster);
    }

    @Override
    public void deleteMonster(Long id) {
        Monster monster = new Monster();
        monster.setId(id);

        monsterService.delete(monster);
    }

    @Override
    public MonsterDTO getMonsterById(Long id) {
        return beanMappingService.mapTo(monsterService.findById(id), MonsterDTO.class);
    }

    @Override
    public List<MonsterDTO> getAllMonsters() {
        return beanMappingService.mapTo(monsterService.findAll(), MonsterDTO.class);
    }

    @Override
    public void moveToAnotherHouse(MonsterDTO monster, HouseDTO house) {
        Monster monsterEntity = beanMappingService.mapTo(monster, Monster.class);
        House houseEntity = beanMappingService.mapTo(house, House.class);

        monsterService.moveToAnotherHouse(monsterEntity, houseEntity);
    }

    private House getHouse(Long id) {
        if (id == null) {
            return null;
        }
        House house = houseService.findById(id);
        return house;
    }

    private Ability getAbility(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id of ability was null");
        }
        Ability ability = abilityService.findById(id);
        if (ability == null) {
            throw new IllegalArgumentException("ability with id: " + id.intValue() + " doesnt exist");
        }

        return ability;
    }
}
