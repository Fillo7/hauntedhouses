/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for class Ability.
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
public class AbilityDTO extends AbilityCreateDTO {

    @NotNull
    private Long id;

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
