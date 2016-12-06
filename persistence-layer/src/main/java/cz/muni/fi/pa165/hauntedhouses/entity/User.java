package cz.muni.fi.pa165.hauntedhouses.entity;

import cz.muni.fi.pa165.hauntedhouses.enums.UserRole;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String login;
    
    @NotNull
    @Column(nullable = false)
    private String passwordHash;

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private UserRole userRole;

    public User() {}

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
        if(!(other instanceof User)) {
            return false;
        }

        if(login == null && ((User) other).getLogin() != null) {
            return false;
        }

        return login.equals(((User) other).getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "User: id: " + id + ", login: " + login;
    }
}
