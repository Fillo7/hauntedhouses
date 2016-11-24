package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseUpdateDTO;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation for HouseFacade
 *
 * Created by Ondrej Oravcok on 09-Nov-16.
 */
@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    @Inject
    private HouseService houseService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createHouse(HouseCreateDTO houseCreateDTO) {
        if(houseCreateDTO == null){
            throw new IllegalArgumentException("house cannot be null");
        }

        House house = beanMappingService.mapTo(houseCreateDTO, House.class);

        houseService.create(house);
        return house.getId();
    }

    @Override
    public void updateHouse(HouseUpdateDTO houseUpdateDTO) {
        if(houseUpdateDTO == null){
            throw new IllegalArgumentException("house cannot be null");
        }

        House house = beanMappingService.mapTo(houseUpdateDTO, House.class);
        if(houseService.getById(house.getId()) == null){
            throw new NoEntityException("updating non existing house");
        }

        houseService.update(house);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return beanMappingService.mapTo(houseService.getAll(), HouseDTO.class);
    }

    @Override
    public HouseDTO getHouseById(Long houseId) {
        if(houseId == null){
            throw new IllegalArgumentException("houseId cannot be null");
        }

        House house = houseService.getById(houseId);
        if(house == null){
            throw new NoEntityException("house with id=" + houseId + " does not exist");
        }

        return beanMappingService.mapTo(house, HouseDTO.class);
    }

    @Override
    public HouseDTO getHouseByName(String houseName) {
        if(houseName == null){
            throw new IllegalArgumentException("houseName cannot be null");
        }

        House house = houseService.getByName(houseName);
        if(house == null){
            throw new NoEntityException("house with name=" + houseName + " does not exist");
        }

        return beanMappingService.mapTo(house, HouseDTO.class);
    }

    @Override
    public void deleteHouse(Long houseId) {
        if(houseId == null){
            throw new IllegalArgumentException("houseId cannot be null");
        }

        House house = houseService.getById(houseId);
        if(house == null){
            throw new NoEntityException("house with id=" + houseId + " does not exist, cannot remove");
        }

        houseService.delete(house);
    }

    @Override
    public void purge(HouseDTO house) {
        if (house == null) {
            throw new IllegalArgumentException("House is null.");
        }

        House houseEntity = beanMappingService.mapTo(house, House.class);

        houseService.purge(houseEntity);
    }
}
