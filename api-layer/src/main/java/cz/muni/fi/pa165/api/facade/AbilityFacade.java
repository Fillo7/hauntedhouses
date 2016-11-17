package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.AbilityCreateDTO;
import java.util.List;
import cz.muni.fi.pa165.api.dto.AbilityDTO;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
public interface AbilityFacade {

    /**
     * Create new ability.
     * @param abilityCreateDTO Ability data transfer object.
     * @return ID of the newly created ability.
     */
    Long createAbility(AbilityCreateDTO abilityCreateDTO);

    /**
     * Updates existing ability.
     * @param abilityDTO Ability data transfer object.
     */
    void updateAbility(AbilityDTO abilityDTO);

    /**
     * Deletes ability with given ID.
     * @param id ID of the ability that is to be deleted.
     */
    void removeAbility(Long id);

    /**
     * Retrieves the ability by its ID.
     * @param id ID of the ability. Unique.
     * @return Ability data transfer object.
     */
    AbilityDTO getAbilityById(Long id);

    /**
     * Retrieves all abilities.
     * @return List of ability data transfer objects.
     */
    List<AbilityDTO> getAllAbilities();
}
