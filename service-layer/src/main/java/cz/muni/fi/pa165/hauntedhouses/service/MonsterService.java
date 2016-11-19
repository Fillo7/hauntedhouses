package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;

/**
 *
 * @author Marek Janco
 */
public interface MonsterService {
    
    /**
     * creates monster
     * 
     * @param m monster that will be created
     * @throws IllegalArgumentException if m is null
     */
    public void create(Monster m);
    
    /**
     * updates monster
     * 
     * @param m monster that will be updated
     * @return updated monster
     * @throws IllegalArgumentException if m is null
     */
    public Monster update(Monster m);
    
    
    /**
     * deletes monster
     * 
     * @param m monster that will be deleted
     * @throws IllegalArgumentException if m is null
     */
    public void delete(Monster m);
    
    /**
     * finds monster by id
     * 
     * @param id of monster to be found
     * @return Monster with given id
     */
    public Monster findById(Long id);
    
    /**
     * finds all monsters
     * 
     * @return list of all monsters
     */
    public List<Monster> findAll();
}
