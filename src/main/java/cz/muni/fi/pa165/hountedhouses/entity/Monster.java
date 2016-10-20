/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hountedhouses.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marek Janco
 */
@Entity
@Table(name = "Monsters")
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String name;
    
    @NotNull
    private String description;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date hauntedIntervalStart;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date hauntedIntervalEnd;

    public Monster() {
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getHauntedIntervalEnd() {
        return hauntedIntervalEnd;
    }

    public Date getHauntedIntervalStart() {
        return hauntedIntervalStart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHauntedIntervalEnd(Date hauntedIntervalEnd) {
        if (!hauntedIntervalEnd.after(this.hauntedIntervalStart)){
            throw new IllegalArgumentException("end of hauntedInterval si before start");
        }
        this.hauntedIntervalEnd = hauntedIntervalEnd;
    }

    public void setHauntedIntervalStart(Date hauntedIntervalStart) {
        if (!hauntedIntervalStart.before(hauntedIntervalEnd)) {
            throw new IllegalArgumentException("start of hauntedInterval si after end");
        }

        this.hauntedIntervalStart = hauntedIntervalStart;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Monster)){
            return false;
        }
        
        final Monster monster = (Monster) obj;
        if( !monster.getId().equals( this.getId() ) ){
            return false;
        }
        return true;
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
