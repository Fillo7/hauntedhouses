package cz.muni.fi.pa165.api.facade;

import cz.muni.fi.pa165.api.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.api.dto.MonsterDTO;
import java.util.List;

/**
 *
 * @author Marek Janco
 */
public interface MonsterFacade {
    /**
     * creates monster
     * 
     * @param monsterCreateDTO MonsterCreateDTO that will be crated
     * @return id of created monster
     */
    public Long createMonster(MonsterCreateDTO monsterCreateDTO);
    
    /**
     * update existing monster
     * 
     * @param monsterDTO MosnterDTO that will be updated
     */
    public void updateMonster(MonsterDTO monsterDTO);
    
    /**
     * delete existing monster
     * 
     * @param id Monster's that will be deleted 
     */
    public void deleteMonster(Long id);
    
    /**
     * finds existing monster by id
     * 
     * @param id of specific monster
     * @return MonsterDTO with searched id if exist, if not returns null
     */
    public MonsterDTO getMonsterById(Long id);

    /**
     * find all monsters
     * 
     * @return list of all monsters
     */
    public List<MonsterDTO> getAllMonsters();
}
