package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;

import javax.validation.constraints.NotNull;

/**
 * @author Filip Petrovic (422334)
 */
public class CursedObjectIncreaseFactorDTO {
    @NotNull
    private MonsterAttractionFactor threshold;

    public MonsterAttractionFactor getThreshold() {
        return threshold;
    }

    public void setThreshold(MonsterAttractionFactor threshold) {
        this.threshold = threshold;
    }
}
