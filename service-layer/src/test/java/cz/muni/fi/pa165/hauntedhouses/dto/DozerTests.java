package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.dto.create.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
//        Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();    //potrebuje custom XML mapping
        Mapper mapper = new DozerBeanMapper();
        HouseCreateDTO houseDto = mapper.map(houseComplete, HouseCreateDTO.class);
        Assert.assertNotNull(houseDto);
        Assert.assertEquals(houseDto.getName(), "White house");
    }

    @Test
    public void testDozerToEntity(){
        Mapper mapper = new DozerBeanMapper();
        House house = mapper.map(houseCreateDto, House.class);
        Assert.assertNotNull(house);
        Assert.assertEquals(house.getAddress(), "Kounicova 50");
        Assert.assertEquals(house.getName(), "Mordor");
    }

//    @Test
//    public void testToHouseDTO(){
//        houseDao.create(houseComplete);
//        House h = houseDao.findAll().get(0);
//
//        Mapper mapper = new DozerBeanMapper();
//        HouseDTO houseDTO = mapper.map(h, HouseDTO.class);
//        Assert.assertNotNull(houseDTO);
//        Assert.assertEquals(houseDTO.getName(), "White house");
//        Assert.assertEquals(houseDTO.getAddress(), "1600 Pennsylvania Ave NW, Washington, DC 20500");
//    }

}
