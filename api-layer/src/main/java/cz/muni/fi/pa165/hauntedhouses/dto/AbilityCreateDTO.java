/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
public class AbilityCreateDTO {

    @NotNull
    private String name;

    private String description;

    private Set<MonsterDTO> monsters = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MonsterDTO> getMonsters() {
        return Collections.unmodifiableSet(monsters);
    }

    public void setMonsters(Set<MonsterDTO> monsters) {
        this.monsters = monsters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        AbilityCreateDTO other = (AbilityCreateDTO) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
