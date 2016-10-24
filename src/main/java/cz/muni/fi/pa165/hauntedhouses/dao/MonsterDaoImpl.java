package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Marek Janco
 */
@Repository
@Transactional
public class MonsterDaoImpl implements MonsterDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void create(Monster monster) {
        em.persist(monster);
    }

    @Override
    public void update(Monster monster) {
        em.merge(monster);
    }

    @Override
    public void delete(Monster monster) {
        em.remove(monster);
    }

    @Override
    public Monster findById(Long Id) {
        return em.find(Monster.class, Id);
    }

    @Override
    public List<Monster> findAll() {
        return em.createQuery("SELECT m FROM Monster m", Monster.class).getResultList();
    }
    
}
