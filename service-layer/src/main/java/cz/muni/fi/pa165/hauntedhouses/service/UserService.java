package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.entity.User;
import java.util.List;

/**
 * @author Filip Petrovic (422334)
 */
public interface UserService {
    /**
     * Adds new user.
     * @param user User to be added.
     * @param unencryptedPassword Password of the new user.
     */
    void create(User user, String unencryptedPassword);

    /**
     * Deletes existing user.
     * @param user User to be deleted.
     */
    void delete(User user);

    /**
     * Returns existing user with given id.
     * @param id Id of a user to be returned.
     * @return Existing user with given id.
     */
    User getById(Long id);

    /**
     * Returns existing user with given login.
     * @param login Login of a user to be returned.
     * @return Existing user with given login.
     */
    User getByLogin(String login);

    /**
     * Returns list of all users.
     * @return List of all users.
     */
    List<User> getAll();
    
    /**
     * Authenticates a user. Return true if the passwords match.
     * @param user User to be authenticated.
     * @param password Hashed password for comparison.
     * @return True if authentication succeeds, false otherwise.
     */
    boolean authenticate(User user, String password);
}
