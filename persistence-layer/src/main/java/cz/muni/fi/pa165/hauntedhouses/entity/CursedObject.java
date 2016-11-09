package cz.muni.fi.pa165.hauntedhouses.entity;

import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Filip Petrovic (422334)
 */
@Entity
@Table(name = "CursedObject")
public class CursedObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @Enumerated
    @NotNull
    @Column(nullable = false)
    private MonsterAttractionFactor monsterAttractionFactor;

    @ManyToOne(optional=false)
    @NotNull
    private House house;

    public CursedObject() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public MonsterAttractionFactor getMonsterAttractionFactor() {
        return monsterAttractionFactor;
    }

    public void setMonsterAttractionFactor(MonsterAttractionFactor monsterAttractionFactor) {
        this.monsterAttractionFactor = monsterAttractionFactor;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Monster)) return false;

        if(name == null && ((CursedObject) other).getName() != null) {
            return false;
        }

        return name.equals(((CursedObject) other).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
