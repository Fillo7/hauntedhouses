package cz.muni.fi.pa165.hauntedhouses.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ondro on 17-Oct-16.
 */
@Entity
@Table(name = "Houses")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String address;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private Set<Monster> monsters = new HashSet<>();

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

    public Set<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster monster){
        this.monsters.add(monster);
    }

    public void removeMonster(Monster monster){
        this.monsters.remove(monster);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof House))
            return false;

        House house = (House) obj;
        if(name == null){
            if(house.name != null)
                return false;
        }
        else{
            if(name.equals(house.name))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return name != null ? name.hashCode() : 0;
    }
}
