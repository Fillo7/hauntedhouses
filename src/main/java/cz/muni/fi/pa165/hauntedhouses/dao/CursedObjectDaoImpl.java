package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * @author Filip Petrovic (422334)
 */
@Repository
@Transactional
public class CursedObjectDaoImpl implements CursedObjectDao {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void addCursedObject(CursedObject cursedObject) {
        if(cursedObject == null) {
            throw new IllegalArgumentException("cursedObject is set to null.");
        }
        
        if(cursedObject.getId() != null) {
            throw new IllegalArgumentException("id is already set.");
        }
        
        entityManager.persist(cursedObject);
    }
    
    @Override
    public CursedObject updateCursedObject(CursedObject cursedObject) {
        if(cursedObject == null) {
            throw new IllegalArgumentException("cursedObject is set to null.");
        }
        
        return entityManager.merge(cursedObject);
    }
    
    @Override
    public void deleteCursedObject(CursedObject cursedObject) {
        if(cursedObject == null) {
            throw new IllegalArgumentException("cursedObject is set to null.");
        }
        
        entityManager.remove(getCursedObject(cursedObject.getId()));
    }
    
    @Override
    public CursedObject getCursedObject(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("id is set to null.");
        }
        
        return entityManager.find(CursedObject.class, id);
    }
    
    @Override
    public List<CursedObject> getAllCursedObjects() {
        return entityManager.createQuery("SELECT co FROM CursedObject co", CursedObject.class).getResultList();
    }
}
