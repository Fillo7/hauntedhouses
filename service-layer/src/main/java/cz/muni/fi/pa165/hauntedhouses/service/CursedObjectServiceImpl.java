package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.CursedObjectDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
public class CursedObjectServiceImpl implements CursedObjectService {
    @Inject
    private CursedObjectDao cursedObjectDao;
    
    @Override
    public void create(CursedObject cursedObject) {
        cursedObjectDao.create(cursedObject);
    }

    @Override
    public CursedObject update(CursedObject cursedObject) {
        return cursedObjectDao.update(cursedObject);
    }

    @Override
    public void delete(CursedObject cursedObject) {
        cursedObjectDao.delete(cursedObject);
    }

    @Override
    public CursedObject findById(Long id) {
        return cursedObjectDao.findById(id);
    }

    @Override
    public List<CursedObject> findAll() {
        return cursedObjectDao.findAll();
    }
}
