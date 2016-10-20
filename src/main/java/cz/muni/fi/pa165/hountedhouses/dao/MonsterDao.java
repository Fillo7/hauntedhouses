package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.entity.Monster;
import java.util.List;

/**
 *
 * @author Marek Janco
 */
public interface MonsterDao {
    
    /*
    * create a monster in DB
    *
    * @param monster to be created
    */
    public void create(Monster monster);
    
    /*
    * update monster, which is saved in DB
    *
    * @param monster to be updated
    */
    public void update(Monster monster);
    /*
    * delete monster from DB
    *
    * @param monster to be deleted
    */
    public void delete(Monster monster);
    
    /*
    * Find monster in DB  
    *
    * @param id of monster that should be found
    * @return Monster if found, null if not
    */
    public Monster findById(Long Id);
    
    /*
    * List all monsters that are in DB
    *
    * @return List of all monsters
    */
    public List<Monster> findAll();
}
