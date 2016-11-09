package cz.muni.fi.pa165.hauntedhouses.entity;

import java.util.Collections;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
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
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy="house")
    private Set<Monster> monsters = new HashSet<>();

    @OneToMany(mappedBy="house")
    private Set<CursedObject> cursedObjects = new HashSet<>();

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
        return Collections.unmodifiableSet(monsters);
    }

    public void addMonster(Monster monster){
        this.monsters.add(monster);
    }

    public void removeMonster(Monster monster){
        this.monsters.remove(monster);
    }

    public Set<CursedObject> getCursedObjects() {
        return Collections.unmodifiableSet(cursedObjects);
    }

    public void addCursedObject(CursedObject cursedObject){
        this.cursedObjects.add(cursedObject);
    }

    public void removeCursedObject(CursedObject cursedObject){
        this.cursedObjects.remove(cursedObject);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof House)) return false;

        final House other = (House) obj;
        if (name != null ? !name.equals(other.name) : other.name != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }
}
