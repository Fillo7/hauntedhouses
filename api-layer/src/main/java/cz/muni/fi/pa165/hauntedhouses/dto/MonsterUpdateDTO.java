package cz.muni.fi.pa165.hauntedhouses.dto;

/**
 * Created by User on 23-Nov-16.
 */
public class MonsterUpdateDTO extends MonsterCreateDTO {

    private Long id;

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
