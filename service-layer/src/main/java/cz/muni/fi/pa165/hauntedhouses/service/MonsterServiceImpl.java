package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Marek Janco
 */
@Service
//@ServiceExceptionTranslate
public class MonsterServiceImpl implements MonsterService{

    @Inject
    private MonsterDao monsterDao;

    @Override
    public void create(Monster m) {
        monsterDao.create(m);
    }

    @Override
    public Monster update(Monster m) {
        return monsterDao.update(m);
    }

    @Override
    public void delete(Monster m) {
        monsterDao.delete(m);
    }

    @Override
    public Monster getById(Long id) {
        return monsterDao.getById(id);
    }

    @Override
    public List<Monster> getAll() {
        return monsterDao.getAll();
    }

    @Override
    public void moveToAnotherHouse(Monster monster, House house) {
        if (monster == null) {
            throw new IllegalArgumentException("Null monster cannot be moved.");
        }
        if (house == null) {
            throw new IllegalArgumentException("Monster cannot be moved to null house.");
        }

        if (monsterDao.getById(monster.getId()) == null) {
            throw new DataManipulationException("The monster cannot be found in the database.");
        }
        
        monster.setHouse(house);
        monsterDao.update(monster);
    }
}
