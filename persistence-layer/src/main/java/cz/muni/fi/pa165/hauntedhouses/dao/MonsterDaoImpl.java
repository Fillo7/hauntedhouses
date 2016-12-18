package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Marek Janco
 */
@Repository
public class MonsterDaoImpl implements MonsterDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Monster monster) {
        em.persist(monster);
    }

    @Override
    public Monster update(Monster monster) {
        return em.merge(monster);
    }

    @Override
    public void delete(Monster monster) {
        if(monster == null){
            throw new IllegalArgumentException("Deleting null entity.");
        }
        
        em.remove(getById(monster.getId()));
    }

    @Override
    public Monster getById(Long id) {
        return em.find(Monster.class, id);
    }

    @Override
    public List<Monster> getAll() {
        return em.createQuery("SELECT m FROM Monster m", Monster.class).getResultList();
    }

    @Override
    public Monster getByName(String name) {
        try{
            TypedQuery<Monster> query = em.createQuery("SELECT m FROM Monster m WHERE m.name = :name",
                    Monster.class).setParameter("name", name);
            return query.getSingleResult();
        } catch(NoResultException e){
            return null;
        }
    }

}
