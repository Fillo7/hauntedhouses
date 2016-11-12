package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.api.dto.HouseCreateDTO;
import cz.muni.fi.pa165.api.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Ondro on 09-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class DozerTests extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Inject
    HouseDao houseDao;

    House houseComplete;
    HouseCreateDTO houseCreateDto;

    @BeforeMethod
    public void setUp(){
        houseComplete = new House();
        houseComplete.setName("White house");
        houseComplete.setAddress("1600 Pennsylvania Ave NW, Washington, DC 20500");

        houseCreateDto = new HouseCreateDTO();
        houseCreateDto.setAddress("Kounicova 50");
        houseCreateDto.setName("Mordor");
    }
    
    @Test
    public void testDozerFromEntity(){
        HouseCreateDTO houseDto = beanMappingService.mapTo(houseComplete, HouseCreateDTO.class);
        Assert.assertNotNull(houseDto);
        Assert.assertEquals(houseDto.getName(), "White house");
    }

    @Test
    public void testDozerToEntity(){
        House house = beanMappingService.mapTo(houseCreateDto, House.class);
        Assert.assertNotNull(house);
        Assert.assertEquals(house.getAddress(), "Kounicova 50");
        Assert.assertEquals(house.getName(), "Mordor");
    }
/*
    @Test
    public void testToHouseDTO(){
        houseDao.create(houseComplete);
        House h = houseDao.findAll().get(0);

        HouseDTO houseDTO = beanMappingService.mapTo(h, HouseDTO.class);
        Assert.assertNotNull(houseDTO);
        Assert.assertEquals(houseDTO.getName(), "White house");
        Assert.assertEquals(houseDTO.getAddress(), "1600 Pennsylvania Ave NW, Washington, DC 20500");
    }*/
}
