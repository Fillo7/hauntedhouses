package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class UserAuthenticateDTO {
    @NotNull
    private String login;

    @NotNull
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
