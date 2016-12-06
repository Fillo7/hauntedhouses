package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.User;
import java.util.List;

/**
 * @author Filip Petrovic (422334)
 */
public interface UserDao {
    /**
     * Adds new user.
     * @param user User to be added.
     */
    void create(User user);

    /**
     * Deletes existing user.
     * @param user User to be deleted.
     * @throws IllegalArgumentException when user is null.
     */
    void delete(User user);

    /**
     * Returns existing user with given id.
     * @param id Id of a user to be returned.
     * @return Existing user with given id.
     * @throws IllegalArgumentException when id is null.
     */
    User getById(Long id);

    /**
     * Returns list of all users.
     * @return List of all users.
     */
    List<User> getAll();
}
