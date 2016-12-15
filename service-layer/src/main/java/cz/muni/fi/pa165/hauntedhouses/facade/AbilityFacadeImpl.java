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
 * @version 15.12.2016
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

        Ability ability = beanMappingService.mapTo(abilityCreateDTO, Ability.class);

        // IDs to entities by Filip/Ondro's template
        for (Long monsterId : abilityCreateDTO.getMonsterIds()){
            Monster monster = monsterService.getById(monsterId);
            if (monster != null){
                ability.addMonster(monster);
            }
        }

        abilityService.create(ability);

        return ability.getId();
    }

    @Override
    public void updateAbility(AbilityDTO abilityDTO) {
        if (abilityDTO == null) {
            throw new IllegalArgumentException("AbilityDTO is null.");
        }

        Ability ability = beanMappingService.mapTo(abilityDTO, Ability.class);

        // IDs to entities by Filip/Ondro's template
        for (Long monsterId : abilityDTO.getMonsterIds()){
            Monster monster = monsterService.getById(monsterId);
            if (monster != null){
                ability.addMonster(monster);
            }
        }

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

        Ability ability = abilityService.getById(id);
        AbilityDTO resultAbilityDTO = beanMappingService.mapTo(ability, AbilityDTO.class);

        // Entities to IDs by Filip/Ondro's template
        if (ability.getMonsters() != null){
            ability.getMonsters().forEach(current -> resultAbilityDTO.addMonsterId(current.getId()));
        }

        return resultAbilityDTO;
    }

    @Override
    public AbilityDTO getAbilityByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name of ability is null.");
        }

        Ability ability = abilityService.getByName(name);
        AbilityDTO resultAbilityDTO = beanMappingService.mapTo(ability, AbilityDTO.class);

        // Entities to IDs by Filip/Ondro's template
        if (ability.getMonsters() != null){
            ability.getMonsters().forEach(current -> resultAbilityDTO.addMonsterId(current.getId()));
        }

        return resultAbilityDTO;
    }

    @Override
    public List<AbilityDTO> getAllAbilities() {
        List<AbilityDTO> resultAbilities = beanMappingService.mapTo(abilityService.getAll(), AbilityDTO.class);

        // Entities to IDs by Filip/Ondro's template
        for (AbilityDTO abilityDTO : resultAbilities) {

            Ability ability = abilityService.getById(abilityDTO.getId());

            if (ability != null) {
                Set<Monster> monsters = ability.getMonsters();
                monsters.forEach(monster -> abilityDTO.addMonsterId(monster.getId()));
            }
        }

        return resultAbilities;
    }
}
