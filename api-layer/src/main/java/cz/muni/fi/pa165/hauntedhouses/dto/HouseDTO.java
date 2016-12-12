package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO object for House
 *
 * Created by Ondro on 09-Nov-16.
 */
public class HouseDTO extends HouseCreateDTO {

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
    public boolean equals(Object obj){
        return super.equals(obj);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
