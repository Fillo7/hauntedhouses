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
        // Prevent endless loop
        if (monsters.contains(monster)) {
            return;
        }

        // Add new monster
        this.monsters.add(monster);

        // Set this house as the monster's house
        monster.setHouse(this);
    }

    public void removeMonster(Monster monster){
        // Prevent endless loop
        if (!monsters.contains(monster)) {
            return;
        }

        // Remove the monster
        this.monsters.remove(monster);

        // Remove this house as the monster's house
        monster.setHouse(null);
    }

    public Set<CursedObject> getCursedObjects() {
        return Collections.unmodifiableSet(cursedObjects);
    }

    public void addCursedObject(CursedObject cursedObject){
        // Prevent endless loop
        if (cursedObjects.contains(cursedObject)) {
            return;
        }

        // Add new cursed object
        this.cursedObjects.add(cursedObject);

        // Set this house as the cursed object's house
        cursedObject.setHouse(this);
    }

    public void removeCursedObject(CursedObject cursedObject){
        // Prevent endless loop
        if (!cursedObjects.contains(cursedObject)) {
            return;
        }

        // Remove the cursed object
        this.cursedObjects.remove(cursedObject);

        // Remove this house as the cursed object's house
        cursedObject.setHouse(null);
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

    @Override
    public String toString() {
        return "House: id: " + id + ", name: " + name + ", address: " + address
                + ", monsters size: " + monsters.size() + ", cursed objects size: " + cursedObjects.size();
    }
}
