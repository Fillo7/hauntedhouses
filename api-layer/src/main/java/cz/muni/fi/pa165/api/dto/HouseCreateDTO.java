package cz.muni.fi.pa165.api.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Ondro on 09-Nov-16.
 */
public class HouseCreateDTO {

    @NotNull
    private String address;

    @NotNull
    private String name;

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
