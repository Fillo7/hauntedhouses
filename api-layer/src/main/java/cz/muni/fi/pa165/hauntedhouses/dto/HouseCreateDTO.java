package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.HashSet;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

/**
 * DTO object for creating House
 *
 * Created by Ondro on 09-Nov-16.
 */
public class HouseCreateDTO {

    @NotNull
    private String address;

    @NotNull
    private String name;

    private Set<Long> monsterIds = new HashSet<>();

    private Set<Long> cursedObjectIds = new HashSet<>();
    
    //getters and setters

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getMonsterIds() {
        return monsterIds;
    }

    public void addMonsterId(Long monsterId) {
        this.monsterIds.add(monsterId);
    }

    public Set<Long> getCursedObjectIds() {
        return cursedObjectIds;
    }

    public void addCursedObjectId(Long cursedObjectId) {
        this.cursedObjectIds.add(cursedObjectId);
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(obj == null) return false;
        if(!(obj instanceof HouseCreateDTO)) return false;

        final HouseCreateDTO other = (HouseCreateDTO) obj;
        if (getName() != null ? !getName().equals(other.getName()) : other.getName() != null) return false;
        if (getAddress() != null ? !getAddress().equals(other.getAddress()) : other.getAddress() != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, address);
    }

    @Override
    public String toString(){
        return "HouseCreateDTO{name=" + getName() + ", address=" + getAddress() + "}";
    }
}
