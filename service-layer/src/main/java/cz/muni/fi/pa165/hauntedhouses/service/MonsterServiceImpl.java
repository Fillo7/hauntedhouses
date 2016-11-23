package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Marek Janco
 */
@Service
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
    public Monster findById(Long id) {
        return monsterDao.findById(id);
    }

    @Override
    public List<Monster> findAll() {
        return monsterDao.findAll();
    }
    
}
