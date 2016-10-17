package cz.muni.fi.pa165.hountedhouses.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Ondro on 17-Oct-16.
 */
@Entity
@Table(name = "Users")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String address;

    @NotNull
    private String name;

    public House(){
    }

    public House(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
