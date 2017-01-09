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

    private Set<Long> abilityIds = new HashSet<>();

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

    public Set<Long> getAbilityIds() {
        return abilityIds;
    }

    public void addAbilityId(Long abilityId) {
        this.abilityIds.add(id);
    }
    
    public void addAllAbilityIds(Set<Long> abilities) {
        this.abilityIds.addAll(abilities);
    }

    public void removeAbilityId(Long abilityId) {
        this.abilityIds.remove(abilityId);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof MonsterDTO)) {
            return false;
        }

        if(name == null && ((MonsterDTO) other).getName() != null) {
            return false;
        }

        return name.equals(((MonsterDTO) other).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MonsterDTO( name: "+this.getName()+", description: "+this.getDescription()+")";
    }

}