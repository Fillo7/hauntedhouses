package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterUpdateDTO;

import java.util.List;

/**
 *
 * @author Marek Janco
 */
public interface MonsterFacade {

    /**
     * creates monster
     *
     * @param m monster to be created
     * @return id of created monster
     */
    public Long createMonster(MonsterCreateDTO m);

    /**
     * updates existing monster
     *
     * @param m monster to be updated
     * @throws
     */
    public void updateMonster(MonsterUpdateDTO m);

    /**
     * deletes existing monster
     *
     * @param id of monster to be deleted
     */
    public void deleteMonster(Long id);

     /**
      * finds existing monster by id
      *
      * @param id of specific monster
      * @return MonsterDTO with id if exist, if not returns null
      */
    public MonsterDTO getMonsterById(Long id);

    /**
      * find all monsters
      *
      * @return list of all monsters
      */
     public List<MonsterDTO> getAllMonsters();

     /**
     * Moves the monster into another house.
     * @param monster Monster that is to be moved.
     * @param house House that the monster is moving into.
     */
    public void moveToAnotherHouse(MonsterDTO monster, HouseDTO house);
}
