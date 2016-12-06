package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.enums.UserRole;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class UserCreateDTO {
    @NotNull
    private String login;

    @NotNull
    private UserRole userRole;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof UserCreateDTO)) {
            return false;
        }

        if(login == null && ((UserCreateDTO) other).getLogin() != null) {
            return false;
        }

        return login.equals(((UserCreateDTO) other).getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
    
    @Override
    public String toString(){
        return "UserCreateDTO{login = " + getLogin() + "}";
    }
}
