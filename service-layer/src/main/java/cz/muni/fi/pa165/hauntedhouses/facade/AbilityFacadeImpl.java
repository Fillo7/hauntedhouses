package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
@Service
@Transactional
public class AbilityFacadeImpl implements AbilityFacade {
    @Inject
    private AbilityService abilityService;
    
    @Inject
    private MonsterService monsterService;
    
    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createAbility(AbilityCreateDTO abilityCreateDTO) {
        if (abilityCreateDTO == null) {
            throw new IllegalArgumentException("AbilityCreateDTO is null.");
        }

        Ability ability = new Ability();
        ability.setName(abilityCreateDTO.getName());
        ability.setDescription(abilityCreateDTO.getDescription());
        Set<Long> monsterIds = abilityCreateDTO.getMonsterIds();
        monsterIds.forEach(monsterId -> ability.addMonster(monsterService.getById(monsterId)));

        abilityService.create(ability);
        return ability.getId();
    }

    @Override
    public void updateAbility(AbilityDTO abilityDTO) {
        if (abilityDTO == null) {
            throw new IllegalArgumentException("AbilityDTO is null.");
        }

        Ability ability = new Ability();
        ability.setId(abilityDTO.getId());
        ability.setName(abilityDTO.getName());
        ability.setDescription(abilityDTO.getDescription());
        Set<MonsterDTO> monsters = abilityDTO.getMonsters();
        monsters.forEach(monster -> ability.addMonster(beanMappingService.mapTo(monster, Monster.class)));

        abilityService.update(ability);
    }

    @Override
    public void deleteAbility(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID of ability is null.");
        }

        Ability ability = new Ability();
        ability.setId(id);

        abilityService.delete(ability);
    }

    @Override
    public AbilityDTO getAbilityById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID of ability is null.");
        }

        return beanMappingService.mapTo(abilityService.getById(id), AbilityDTO.class);
    }

    @Override
    public AbilityDTO getAbilityByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name of ability is null.");
        }

        return beanMappingService.mapTo(abilityService.getByName(name), AbilityDTO.class);
    }

    @Override
    public List<AbilityDTO> getAllAbilities() {
        return beanMappingService.mapTo(abilityService.getAll(), AbilityDTO.class);
    }
}
