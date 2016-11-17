package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.api.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.api.dto.AbilityDTO;
import cz.muni.fi.pa165.api.dto.MonsterDTO;
import cz.muni.fi.pa165.api.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
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
    private BeanMappingService beanMappingService;

    @Override
    public Long createAbility(AbilityCreateDTO abilityCreateDTO) {
        Ability ability = new Ability();

        ability.setName(abilityCreateDTO.getName());
        ability.setDescription(abilityCreateDTO.getDescription());
        Set<MonsterDTO> monsters = abilityCreateDTO.getMonsters();
        monsters.forEach(monster -> ability.addMonster(beanMappingService.mapTo(monster, Monster.class)));

        abilityService.create(ability);
        return ability.getId();
    }

    @Override
    public void updateAbility(AbilityDTO abilityDTO) {
        Ability ability = new Ability();

        ability.setId(abilityDTO.getId());
        ability.setName(abilityDTO.getName());
        ability.setDescription(abilityDTO.getDescription());
        Set<MonsterDTO> monsters = abilityDTO.getMonsters();
        monsters.forEach(monster -> ability.addMonster(beanMappingService.mapTo(monster, Monster.class)));

        abilityService.update(ability);
    }

    @Override
    public void removeAbility(Long id) {
        Ability ability = new Ability();
        ability.setId(id);

        abilityService.remove(ability);
    }

    @Override
    public AbilityDTO getAbilityById(Long id) {
        return beanMappingService.mapTo(abilityService.findById(id), AbilityDTO.class);
    }

    @Override
    public List<AbilityDTO> getAllAbilities() {
        return beanMappingService.mapTo(abilityService.findAll(), AbilityDTO.class);
    }
}
