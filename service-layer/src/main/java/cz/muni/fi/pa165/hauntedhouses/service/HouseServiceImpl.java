package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.CursedObjectDao;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import java.util.HashSet;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Implementation of HouseService
 *
 * Created by Ondro on 09-Nov-16.
 */
@Service
//@ServiceExceptionTranslate
public class HouseServiceImpl implements HouseService {

    @Inject
    private HouseDao houseDao;
    
    @Inject
    private MonsterDao monsterDao;
    
    @Inject
    private CursedObjectDao cursedObjectDao;

    @Override
    public void create(House house) {
        houseDao.create(house);
    }

    @Override
    public House update(House house) {
        return houseDao.update(house);
    }

    @Override
    public House getById(Long id) {
        return houseDao.getById(id);
    }

    @Override
    public House getByName(String name) {
        return houseDao.getByName(name);
    }

    @Override
    public List<House> getAll() {
        return houseDao.getAll();
    }

    @Override
    public void delete(House house) {
        houseDao.delete(house);
    }

    @Override
    public void purge(House house) {
        if (house == null) {
            throw new IllegalArgumentException("House is null, cannot be purged.");
        }

        if (houseDao.getById(house.getId()) == null) {
            throw new DataManipulationException("Given house is not in the database, cannot be purged.");
        }
        
        // New HashSets to allow removal via .forEach()
        Set<Monster> monsters = new HashSet<>(house.getMonsters());
        Set<CursedObject> cursedObjects = new HashSet<>(house.getCursedObjects());
        
        monsters.forEach(monster -> monsterDao.delete(monster));
        cursedObjects.forEach(cursedObject -> cursedObjectDao.delete(cursedObject));

        houseDao.update(house);
    }
}
