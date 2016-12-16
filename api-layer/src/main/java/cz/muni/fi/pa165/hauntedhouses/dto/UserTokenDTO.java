package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.enums.UserRole;
import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class UserTokenDTO {
    @NotNull
    private String login;
    
    @NotNull
    private UserRole userRole;
    
    @NotNull
    private Boolean authenticationResult;

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
    
    public Boolean getAuthenticationResult() {
        return authenticationResult;
    }

    public void setAuthenticationResult(Boolean authenticationResult) {
        this.authenticationResult = authenticationResult;
    }
}
