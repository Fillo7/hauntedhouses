/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kristýna Loukotová
 * @version 21.10.2016
 */
@Repository
public class AbilityDaoImpl implements AbilityDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Ability ability) {
        entityManager.persist(ability);
    }

    @Override
    public Ability update(Ability ability) {
        return entityManager.merge(ability);
    }

    @Override
    public void delete(Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Attempting to delete null Ability entity from the database.");
        }

        Ability abilityInDb = this.getById(ability.getId());
        if (abilityInDb == null) {
            throw new IllegalArgumentException("Attempting to delete Ability entity, "
                    + "but no such entity is currently present in the database.");
        }

        entityManager.remove(abilityInDb);
    }

    @Override
    public Ability getById(Long id) {
        // If find doesn't find anything in the DB, returns null
        return entityManager.find(Ability.class, id);
    }

    @Override
    public Ability getByName(String name) {
        try {
            TypedQuery<Ability> query = entityManager
                    .createQuery("SELECT a FROM Ability a WHERE a.name = :givenName", Ability.class)
                    .setParameter("givenName", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ability> getAll() {
        try {
            TypedQuery<Ability> query = entityManager.createQuery("SELECT a FROM Ability a", Ability.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
