package cz.muni.fi.pa165.hauntedhouses.dto;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marek Janco
 */
public class MonsterDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private LocalTime hauntedIntervalStart;

    @NotNull
    private LocalTime hauntedIntervalEnd;
    
    @NotNull
    private Long houseId;

    private Set<Long> abilities = new HashSet<>();

    //getters and setters

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

    public LocalTime getHauntedIntervalStart() {
        return hauntedIntervalStart;
    }

    public void setHauntedIntervalStart(LocalTime hauntedIntervalStart) {
        this.hauntedIntervalStart = hauntedIntervalStart;
    }

    public LocalTime getHauntedIntervalEnd() {
        return hauntedIntervalEnd;
    }

    public void setHauntedIntervalEnd(LocalTime hauntedIntervalEnd) {
        this.hauntedIntervalEnd = hauntedIntervalEnd;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Set<Long> getAbilities() {
        return abilities;
    }

    public void addAbility(Long ability) {
        this.abilities.add(ability);
    }

    public void addAllAbilities(Set<Long> abilities){
        this.abilities.addAll(abilities);
    }

    public void removeAbility(Long abilityId) {
        this.abilities.remove(abilityId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MonsterDTO)) {
            return false;
        }

        final MonsterDTO monster = (MonsterDTO) obj;
        if (getName() != null ? !getName().equals(monster.getName()) : monster.getName() != null) {
            return false;
        }

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
        return "MonsterDTO( name: "+this.getName()+", description: "+this.getDescription()+")";
    }

}
