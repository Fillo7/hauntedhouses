package cz.muni.fi.pa165.hauntedhouses.dto;

/**
 * DTO object for updating House
 *
 * Created by Ondrej Oravcok on 21-Nov-16.
 */
public class HouseUpdateDTO extends HouseCreateDTO {

    private Long id;

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
