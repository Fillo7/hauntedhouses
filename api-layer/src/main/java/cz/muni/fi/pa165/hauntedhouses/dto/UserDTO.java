package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.enums.UserRole;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class UserDTO {
    @NotNull
    private Long id;

    @NotNull
    private String login;

    @NotNull
    private String passwordHash;

    @NotNull
    private UserRole userRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof UserDTO)) {
            return false;
        }

        if(login == null && ((UserDTO) other).getLogin() != null) {
            return false;
        }

        return login.equals(((UserDTO) other).getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
    
    @Override
    public String toString(){
        return "UserDTO{login = " + getLogin() + "}";
    }
}
