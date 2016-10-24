package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Created by User on 17-Oct-16.
 */
@Repository
@Transactional
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

    @Override
    public House findByName(String name){
        try {
            TypedQuery<House> q = entityManager.createQuery("SELECT h FROM House h WHERE h.name = :givenName",
                    House.class).setParameter("givenName", name);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
