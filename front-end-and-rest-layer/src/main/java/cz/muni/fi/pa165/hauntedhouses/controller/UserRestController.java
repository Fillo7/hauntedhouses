package cz.muni.fi.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserTokenDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.UserFacade;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Filip Petrovic (422334)
 */
@RestController
@RequestMapping(Uri.USERS)
public class UserRestController {
    @Inject
    private UserFacade userFacade;
    
    /**
     * Returns list of users.
     * Example: curl -i -X GET http://localhost:8080/pa165/rest/users
     * @return list of users
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getAllUsers() {
        return userFacade.getAllUsers();
    }
    
    /**
     * Authenticates given user.
     * Example: curl -X POST -i -H "Content-Type: application/json" --data '{"login":"string","password":"string"}' http://localhost:8080/pa165/rest/users/authenticate
     * @param user to be authenticated
     * @return true if authentication was successful, false otherwise
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserTokenDTO authenticate(@RequestBody UserAuthenticateDTO user) {
        return userFacade.authenticate(user);
    }
}
