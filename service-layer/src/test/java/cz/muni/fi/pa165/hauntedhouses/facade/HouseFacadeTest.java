/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseUpdateDTO;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

    private HouseUpdateDTO house1;
    private HouseUpdateDTO house2;

    @BeforeMethod
    public void prepareHouse() {
        house1 = new HouseUpdateDTO();
        house1.setName("Yolo");
        house1.setAddress("Rolo");

        house2 = new HouseUpdateDTO();
        house2.setName("Kolo");
        house2.setAddress("Polo");
    }

    @Test
    public void testCreate() {
        List<HouseDTO> foundHouses = houseFacade.getAllHouses();
        Assert.assertEquals(foundHouses.size(), 0);

        Long createdId = houseFacade.createHouse(house1);

        foundHouses = houseFacade.getAllHouses();
        Assert.assertEquals(foundHouses.size(), 1);
        Assert.assertEquals((foundHouses.get(0).getId()), createdId);
    }

    @Test
    public void testUpdate() {

        Long createdId = houseFacade.createHouse(house1);
        Assert.assertNotNull(createdId);
        HouseDTO foundHouse = houseFacade.getHouseById(createdId);

        this.assertHouseEquals(foundHouse, house1);

        house1.setName("Changed name");
        house1.setAddress("Changed address");

        foundHouse = houseFacade.getHouseById(createdId);

        Assert.assertNotEquals(foundHouse.getName(), house1.getName());
        Assert.assertNotEquals(foundHouse.getAddress(), house1.getAddress());

        house1.setId(createdId);
        houseFacade.updateHouse(house1);
        foundHouse = houseFacade.getHouseById(createdId);

        this.assertHouseEquals(foundHouse, house1);
    }

    @Test
    public void testDelete() {
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
        Long createdId = houseFacade.createHouse(house1);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 1);
        houseFacade.deleteHouse(createdId);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
    }

    @Test
    public void testGetById() {
        houseFacade.createHouse(house2);
        Long createdId = houseFacade.createHouse(house1);
        HouseDTO foundHouse = houseFacade.getHouseById(createdId);

        this.assertHouseEquals(foundHouse, house1);
    }

    @Test
    public void testGetAll() {
        Assert.assertEquals(houseFacade.getAllHouses().size(), 0);
        Long createdId1 = houseFacade.createHouse(house1);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 1);
        Long createdId2 = houseFacade.createHouse(house2);
        Assert.assertEquals(houseFacade.getAllHouses().size(), 2);

        HouseDTO foundHouse1 = houseFacade.getHouseById(createdId1);
        HouseDTO foundHouse2 = houseFacade.getHouseById(createdId2);

        this.assertHouseEquals(foundHouse1, house1);
        this.assertHouseEquals(foundHouse2, house2);
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
