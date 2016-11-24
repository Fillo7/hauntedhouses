/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.entity;

import cz.muni.fi.pa165.hauntedhouses.validation.SurviveCondition;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Objects;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marek Janco
 */
@Entity
@Table(name = "Monsters")
@SurviveCondition(members = {"hauntedIntervalStart", "hauntedIntervalEnd"})
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private LocalTime hauntedIntervalStart;

    @NotNull
    @Column(nullable = false)
    private LocalTime hauntedIntervalEnd;

    @ManyToOne(optional=false)
    private House house;

    @ManyToMany(mappedBy="monsters")
    private Set<Ability> abilities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getHauntedIntervalEnd() {
        return hauntedIntervalEnd;
    }

    public LocalTime getHauntedIntervalStart() {
        return hauntedIntervalStart;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHauntedIntervalEnd(LocalTime hauntedIntervalEnd) {
        this.hauntedIntervalEnd = hauntedIntervalEnd;
    }

    public void setHauntedIntervalStart(LocalTime hauntedIntervalStart) {
        this.hauntedIntervalStart = hauntedIntervalStart;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        // Prevent endless loop
        if (sameAsFormer(house)) {
            return;
        }

        // Set new house
        House oldHouse = this.house;
        this.house = house;

        // Remove from the old house
        if (oldHouse != null) {
            oldHouse.removeMonster(this);
        }

        // Set this monster to new house
        if (house != null) {
            house.addMonster(this);
        }
    }

    public Set<Ability> getAbilities() {
        return Collections.unmodifiableSet(abilities);
    }

    public void removeAbility(Ability ability){
        // Prevent endless loop
        if (!abilities.contains(ability)) {
            return;
        }

        // Remove the ability
        this.abilities.remove(ability);

        // Remove this monster from the ability's monsters
        ability.removeMonster(this);
    }

    public void addAbility(Ability ability) {
        // Prevent endless loop
        if (this.abilities.contains(ability)) {
            return;
        }

        // Add new ability
        this.abilities.add(ability);

        // Set this monster to the ability
        ability.addMonster(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Monster)) return false;

        final Monster monster = (Monster) obj;
        if (getName() != null ? !getName().equals(monster.getName()) : monster.getName() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return "Monster: id: " + id + ", name: " + name + ", description: " + description
                + ", haunt interval: (" + hauntedIntervalStart + ", " + hauntedIntervalEnd
                + "), house id: " + (house == null ? "house is null" : house.getId())
                + ", abilities size: " + abilities.size();
    }

    /**
     * Checks whether given newHouse is the same as this monster's saved house.
     * @param newHouse New house to be linked to this monster.
     * @return True if the two houses are the same. False if not.
     */
    private boolean sameAsFormer(House newHouse) {
        return house == null ? newHouse == null : house.equals(newHouse);
    }
}
