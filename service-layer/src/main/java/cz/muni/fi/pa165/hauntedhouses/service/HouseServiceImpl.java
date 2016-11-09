package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Inject
    private HouseDao houseDao;

    @Override
    public void create(House house) {
        houseDao.create(house);
    }

    @Override
    public House update(House house) {
        return houseDao.update(house);
    }

    @Override
    public House findById(Long id) {
        return houseDao.findById(id);
    }

    @Override
    public List<House> findAll() {
        return houseDao.findAll();
    }

    @Override
    public void remove(House house) {
        houseDao.delete(house);
    }
}
