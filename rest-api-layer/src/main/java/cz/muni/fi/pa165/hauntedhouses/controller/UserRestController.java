package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.exceptions.RequestedResourceNotFound;
import cz.muni.fi.pa165.hauntedhouses.facade.UserFacade;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Petrovic (422334)
 */
@RestController
@RequestMapping("/users")
public class UserRestController {
    @Inject
    private UserFacade userFacade;
    
    /**
     * Returns user with given id.
     * Command: curl -i -X GET
     * http://localhost:8080/pa165/rest/users/{id}
     * @param id identifier of a user
     * @return user with given id
     * @throws RequestedResourceNotFound when given user is not found.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUserById(@PathVariable("id") long id) throws RequestedResourceNotFound {
        try {
            return userFacade.getUserWithId(id);
        } catch (NoEntityException ex) {
            throw new RequestedResourceNotFound("User with given id doesn't exist: " + id, ex);
        }
    }
    
    /**
     * Returns list of users.
     * Command: curl -i -X GET
     * http://localhost:8080/pa165/rest/users
     * @return list of users
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getAllUsers() {
        return userFacade.getAllUsers();
    }
    
    /**
     * Authenticates given user.
     * Command: curl -X PUT -i -H "Content-Type: application/json" --data '{"id":"integer","password":"string"}'
     * http://localhost:8080/pa165/rest/users/authenticate
     * @param user to be authenticated
     * @return true if authentication was successful, false otherwise
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final boolean authenticate(@RequestBody UserAuthenticateDTO user) {
        return userFacade.authenticate(user);
    }
}
