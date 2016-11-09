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
        this.house = house;
    }

    public Set<Ability> getAbilities() {
        return Collections.unmodifiableSet(abilities);
    }

    public void removeAbility(Ability ability){
        this.abilities.remove(ability);
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
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
}
