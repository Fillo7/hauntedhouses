package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.create.HouseCreateDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Ondro on 09-Nov-16.
 */
@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    @Override
    public Long createHouse(HouseCreateDTO house) {
        return null;
    }

    @Override
    public void updateHouse(HouseDTO house) {

    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return null;
    }

    @Override
    public HouseDTO getHouseById(Long houseId) {
        return null;
    }

    @Override
    public void removeHouse(Long houseId) {

    }
}
