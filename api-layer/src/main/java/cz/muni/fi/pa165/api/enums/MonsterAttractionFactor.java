package cz.muni.fi.pa165.api.enums;

/**
 * @author Filip Petrovic (422334)
 */
public enum MonsterAttractionFactor {
    LOW,
    MEDIUM,
    HIGH,
    INSANE {
        @Override
        public MonsterAttractionFactor next() {
            return this;
        }
    };
    
    public MonsterAttractionFactor next() {
        return values()[ordinal() + 1];
    }
}
