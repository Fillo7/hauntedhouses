package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of HouseService
 *
 * Created by Ondro on 09-Nov-16.
 */
@Service
@ServiceExceptionTranslate
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
}
