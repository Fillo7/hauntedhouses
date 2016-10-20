package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.entity.Monster;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return em.createQuery("SELECT m FROM Monsters m", Monster.class).getResultList();
    }
    
}
