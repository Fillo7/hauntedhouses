package cz.muni.fi.pa165.api.dto;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marek Janco
 */
public class MonsterCreateDTO {

    @NotNull
    private String name;
    
    @NotNull
    private String description;
    
    @NotNull
    private LocalTime hauntedIntervalStart;

    @NotNull
    private LocalTime hauntedIntervalEnd;

    private Long houseId;

    private Set<Long> abilities = new HashSet<>();

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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Set<Long> getAbilitiesId() {
        return Collections.unmodifiableSet(abilities);
    }

    public void removeAbilityId(Long abilityId) {
        this.abilities.remove(abilityId);
    }

    public void addAbilityId(Long abilityId) {
        this.abilities.add(abilityId);
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
        return "MonsterDTO( name: "+this.getName()+", descrition: "+this.getDescription()+")";
    }

}
