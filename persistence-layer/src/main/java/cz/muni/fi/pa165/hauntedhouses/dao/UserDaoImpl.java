package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * @author Filip Petrovic (422334)
 */
@Repository
public class UserDaoImpl implements UserDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        if(user == null) {
            throw new IllegalArgumentException("user is set to null.");
        }

        entityManager.remove(getById(user.getId()));
    }

    @Override
    public User getById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("id is set to null.");
        }

        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
