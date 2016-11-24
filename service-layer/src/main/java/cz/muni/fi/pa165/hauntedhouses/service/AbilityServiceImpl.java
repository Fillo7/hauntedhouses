package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
@Service
public class AbilityServiceImpl implements AbilityService {

    @Inject
    private AbilityDao abilityDao;

    @Override
    public void create(Ability cursedObject) {
        abilityDao.create(cursedObject);
    }

    @Override
    public Ability update(Ability cursedObject) {
        return abilityDao.update(cursedObject);
    }

    @Override
    public void delete(Ability cursedObject) {
        abilityDao.delete(cursedObject);
    }

    @Override
    public Ability getById(Long id) {
        return abilityDao.getById(id);
    }

    @Override
    public Ability getByName(String name) {
        return abilityDao.getByName(name);
    }

    @Override
    public List<Ability> getAll() {
        return abilityDao.getAll();
    }
}
