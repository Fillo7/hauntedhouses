/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.HashSet;
import java.util.Objects;
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

    private Set<Long> monsterIds = new HashSet<>();

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

    public Set<Long> getMonsterIds() {
        return monsterIds;
    }

    public void addMonsterId(Long monsterId) {
        monsterIds.add(monsterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbilityCreateDTO)) {
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

    @Override
    public String toString(){
        return "{name=" + name + ", description=" + description + "}";
    }
}
