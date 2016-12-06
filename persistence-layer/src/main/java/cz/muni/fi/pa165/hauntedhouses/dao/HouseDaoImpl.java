package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ondro on 17-Oct-16.
 */
@Repository
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(House house) {
        entityManager.persist(house);
    }

    @Override
    public House update(House house) {
        return entityManager.merge(house);
    }

    @Override
    public void delete(House house) {
        if(house == null){
            throw new IllegalArgumentException("Deleting null entity.");
        }
        entityManager.remove(getById(house.getId()));
    }

    @Override
    public House getById(Long id) {
        return entityManager.find(House.class, id);
    }

    @Override
    public List<House> getAll() {
        TypedQuery<House> query = entityManager.createQuery("SELECT h FROM House h", House.class);
        return query.getResultList();
    }

    @Override
    public House getByName(String name){
        try {
            TypedQuery<House> q = entityManager.createQuery("SELECT h FROM House h WHERE h.name = :givenName",
                    House.class).setParameter("givenName", name);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
