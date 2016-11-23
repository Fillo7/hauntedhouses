package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import java.util.List;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;

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
     * @throws IllegalArgumentException if abilityCreateDTO is null.
     */
    Long createAbility(AbilityCreateDTO abilityCreateDTO);

    /**
     * Updates existing ability.
     * @param abilityDTO Ability data transfer object.
     * @throws IllegalArgumentException if abilityDTO is null.
     */
    void updateAbility(AbilityDTO abilityDTO);

    /**
     * Deletes ability with given ID.
     * @param id ID of the ability that is to be deleted.
     * @throws IllegalArgumentException if id is null.
     */
    void removeAbility(Long id);

    /**
     * Retrieves the ability by its ID.
     * @param id ID of the ability. Unique.
     * @return Ability data transfer object.
     * @throws IllegalArgumentException if id is null.
     */
    AbilityDTO getAbilityById(Long id);

    /**
     * Retrieves the ability by its name.
     * @param name Name of the ability.
     * @return Ability data transfer object.
     * @throws IllegalArgumentException if name is null.
     */
    AbilityDTO getAbilityByName(String name);

    /**
     * Retrieves all abilities.
     * @return List of ability data transfer objects.
     */
    List<AbilityDTO> getAllAbilities();
}
