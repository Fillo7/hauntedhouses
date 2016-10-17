package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.entity.House;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Created by User on 17-Oct-16.
 */
@Repository
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(House house) throws ConstraintViolationException {
        entityManager.persist(house);
    }

    @Override
    public House update(House house) throws ConstraintViolationException {
        return entityManager.merge(house);
    }

    @Override
    public void remove(House house) {
        if(house == null){
            throw new IllegalArgumentException("Deleting null entity.");
        }
        entityManager.remove(findById(house.getId()));
    }

    @Override
    public House findById(Long id) {
        return entityManager.find(House.class, id);
    }

    @Override
    public List<House> findAll() {
        TypedQuery<House> query = entityManager.createQuery("SELECT h FROM House h", House.class);
        return query.getResultList();
    }
}
