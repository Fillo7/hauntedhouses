package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.UserDao;
import cz.muni.fi.pa165.hauntedhouses.entity.User;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;
    
    @Override
    public void create(User user, String unencryptedPassword) {
        // to do
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
    
    @Override
    public boolean authenticate(User user, String password) {
        return false;
        // to do
    }
}
