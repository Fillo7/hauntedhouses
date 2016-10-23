/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.entity.Ability;
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
        if (ability == null) {
            throw new IllegalArgumentException("Attempting to create null Ability entity in the database.");
        }
        
        entityManager.persist(ability);
    }

    @Override
    public void update(Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Attempting to update null Ability entity in the database.");
        }
        
        entityManager.merge(ability);
    }

    @Override
    public void delete(Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Attempting to delete null Ability entity from the database.");
        }
        
        Ability abilityInDb = this.findById(ability.getId());
        if (abilityInDb == null) {
            throw new IllegalArgumentException("Attempting to delete Ability entity, "
                    + "but no such entity is currently present in the database.");
        }
        
        entityManager.remove(abilityInDb);
    }

    @Override
    public Ability findById(Long id) {
        // If find doesn't find anything in the DB, returns null
        return entityManager.find(Ability.class, id);
    }

    @Override
    public Ability findByName(String name) {
        try {
            TypedQuery<Ability> query = entityManager
                    .createQuery("SELECT a FROM Abilities a WHERE a.name = :givenName", Ability.class)
                    .setParameter("givenName", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ability> findAll() {
        try {
            TypedQuery<Ability> query = entityManager.createQuery("SELECT a FROM Abilities a", Ability.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
