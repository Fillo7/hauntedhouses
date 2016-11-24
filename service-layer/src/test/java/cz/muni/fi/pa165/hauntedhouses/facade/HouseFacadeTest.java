/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import java.util.List;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Basic CRUD tests of the House facade layer.
 * @author Kristyna Loukotova
 * @version 22.11.2016
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    HouseFacade houseFacade;

    private HouseCreateDTO createHouse1;
    private HouseCreateDTO createHouse2;

    private HouseDTO house1;
    
    @BeforeMethod
    public void prepareHouse() {
        createHouse1 = new HouseCreateDTO();
        createHouse1.setName("Yolo");
        createHouse1.setAddress("Rolo");

        createHouse2 = new HouseCreateDTO();
        createHouse2.setName("Kolo");
        createHouse2.setAddress("Polo");
        
        house1 = new HouseDTO();
        house1.setName("Changed name");
        house1.setAddress("Changed address");
    }

    @Test
    public void testCreate() {
        List<HouseDTO> foundHouses = houseFacade.getAllHouses();
        Assert.assertEquals(foundHouses.size(), 0);

        Long createdId = houseFacade.createHouse(createHouse1);

        foundHouses = houseFacade.getAllHouses();
        Assert.assertEquals(foundHouses.size(), 1);
        Assert.assertEquals((foundHouses.get(0).getId()), createdId);
    }

    @Test
    public void testUpdate() {

        Long createdId = houseFacade.createHouse(createHouse1);
        Assert.assertNotNull(createdId);
        HouseDTO foundHouse = houseFacade.getHouseById(createdId);

        this.assertHouseEquals(foundHouse, createHouse1);

        house1.setId(createdId);
        houseFacade.updateHouse(house1);
        foundHouse = houseFacade.getHouseById(createdId);

        Assert.assertEquals(house1.getName(), foundHouse.getName());
        Assert.assertEquals(house1.getAddress(), foundHouse.getAddress());
    }

    @Test
    public void testDelete() {
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
        Long createdId = houseFacade.createHouse(createHouse1);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 1);
        houseFacade.deleteHouse(createdId);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
    }

    @Test
    public void testGetById() {
        houseFacade.createHouse(createHouse2);
        Long createdId = houseFacade.createHouse(createHouse1);
        HouseDTO foundHouse = houseFacade.getHouseById(createdId);

        this.assertHouseEquals(foundHouse, createHouse1);
    }

    @Test
    public void testGetAll() {
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
        Long createdId1 = houseFacade.createHouse(createHouse1);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 1);
        Long createdId2 = houseFacade.createHouse(createHouse2);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 2);

        HouseDTO foundHouse1 = houseFacade.getHouseById(createdId1);
        HouseDTO foundHouse2 = houseFacade.getHouseById(createdId2);

        this.assertHouseEquals(foundHouse1, createHouse1);
        this.assertHouseEquals(foundHouse2, createHouse2);
    }

    /**
     * Compares properties of the HouseDTO and House, with exception of ID.
     * @param actual HouseDTO, created by facade layer
     * @param expected HouseCreateDTO, NOT created by facade layer
     */
    private void assertHouseEquals(HouseDTO actual, HouseCreateDTO expected) {
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getAddress(), expected.getAddress());
    }
}
