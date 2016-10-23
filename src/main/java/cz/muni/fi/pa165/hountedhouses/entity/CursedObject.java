package cz.muni.fi.pa165.hountedhouses.entity;

import cz.muni.fi.pa165.hountedhouses.enums.MonsterAttractionFactor;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    private String name;

    @NotNull
    private String description;
    
    @Enumerated
    @NotNull
    private MonsterAttractionFactor monsterAttractionFactor;

    public CursedObject() {}

    public CursedObject(Long id, String name, String description,
                        MonsterAttractionFactor monsterAttractionFactor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.monsterAttractionFactor = monsterAttractionFactor;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
    
    public MonsterAttractionFactor getMonsterAttractionFactor() {
        return monsterAttractionFactor;
    }

    public void setMonsterAttractionFactor(MonsterAttractionFactor monsterAttractionFactor) {
        this.monsterAttractionFactor = monsterAttractionFactor;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof CursedObject)) {
            return false;
        }
        
        return id.equals(((CursedObject)other).id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
