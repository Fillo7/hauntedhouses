package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.DataManipulationException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import org.springframework.dao.DataAccessException;

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

    @Override
    public void create(House house) {
        if(house == null){
            throw new IllegalArgumentException("house is null - cannot create");
        }
        houseDao.create(house);
    }

    @Override
    public House update(House house) {
        if(house == null){
            throw new IllegalArgumentException("house is null - cannot update");
        }
        return houseDao.update(house);
    }

    @Override
    public House findById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("id of house is null");
        }
        return houseDao.findById(id);
    }

    @Override
    public House findByName(String name) {
        if(name == null){
            throw new IllegalArgumentException("name of house is null");
        }
        return houseDao.findByName(name);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }

    @Override
    public void remove(House house) {
        if(house == null){
            throw new IllegalArgumentException("house is null - cannot remove");
        }
        houseDao.delete(house);
    }

    @Override
    public void purge(House house) {
        if (house == null) {
            throw new IllegalArgumentException("House is null, cannot be purged.");
        }

        if (houseDao.findById(house.getId()) == null) {
            throw new DataManipulationException("Given house is not in the database, cannot be purged.");
        }

        Set<Monster> monsters = house.getMonsters();
        Set<CursedObject> cursedObjects = house.getCursedObjects();

        monsters.forEach(monster -> house.removeMonster(monster));
        cursedObjects.forEach(cursedObject -> house.removeCursedObject(cursedObject));

        houseDao.update(house);
    }
}
