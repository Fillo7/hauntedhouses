package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserTokenDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.User;
import cz.muni.fi.pa165.hauntedhouses.service.UserService;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author Filip Petrovic (422334)
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {
    @Inject
    private UserService userService;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public Long createUser(UserCreateDTO userCreateDTO, String unencryptedPassword) {
        if(userCreateDTO == null) {
            throw new IllegalArgumentException("userCreateDTO is null.");
        }
        
        if(unencryptedPassword == null) {
            throw new IllegalArgumentException("unencrypted password is null.");
        }
        
        User user = beanMappingService.mapTo(userCreateDTO, User.class);
        userService.create(user, unencryptedPassword);
        
        return user.getId();
    }

    @Override
    public void deleteUser(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        
        User user = new User();
        user.setId(id);
        
        userService.delete(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Id is null.");
        }
        
        User user = userService.getById(id);
        return beanMappingService.mapTo(user, UserDTO.class);
    }
    
    @Override
    public UserDTO getUserByLogin(String login) {
        if(login == null) {
            throw new IllegalArgumentException("Login is null.");
        }
        
        User user = userService.getByLogin(login);
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAll();
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public UserTokenDTO authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        if(userAuthenticateDTO == null) {
            throw new IllegalArgumentException("userAuthenticateDTO is null.");
        }
        
        User user = userService.getByLogin(userAuthenticateDTO.getLogin());
        UserTokenDTO token = new UserTokenDTO();
        token.setAuthenticationResult(false);
        
        if(user != null) {
            token.setLogin(user.getLogin());
            token.setUserRole(user.getUserRole());
            token.setAuthenticationResult(userService.authenticate(user, userAuthenticateDTO.getPassword()));
        }
        
        return token;
    }
}
