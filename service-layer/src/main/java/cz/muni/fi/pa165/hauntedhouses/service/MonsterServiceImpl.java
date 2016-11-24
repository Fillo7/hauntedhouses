package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslate;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Marek Janco
 */
@Service
@ServiceExceptionTranslate
public class MonsterServiceImpl implements MonsterService{

    @Inject
    private MonsterDao monsterDao;

    @Override
    public void create(Monster m) {
        if(m == null){
            throw new IllegalArgumentException("monster is null - cannot create");
        }
        monsterDao.create(m);
    }

    @Override
    public Monster update(Monster m) {
        if(m == null){
            throw new IllegalArgumentException("monster is null - cannot update");
        }
        return monsterDao.update(m);
    }

    @Override
    public void delete(Monster m) {
        if(m == null){
            throw new IllegalArgumentException("monster is null - cannot delete");
        }
        monsterDao.delete(m);
    }

    @Override
    public Monster findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id of monster is null");
        }
        return monsterDao.findById(id);
    }

    @Override
    public List<Monster> findAll() {
        return monsterDao.findAll();
    }

    @Override
    public void moveToAnotherHouse(Monster monster, House house) {
        if (monsterDao.findById(monster.getId()) == null) {
            throw new DataManipulationException("The monster cannot be found in the database.");
        }

        monster.setHouse(house);
        monsterDao.update(monster);
    }
}
