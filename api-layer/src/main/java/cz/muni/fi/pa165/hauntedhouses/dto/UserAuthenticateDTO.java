package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class UserAuthenticateDTO {
    @NotNull
    private Long id;

    @NotNull
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
