/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class HouseServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private HouseDao houseDao;

    @Autowired
    @InjectMocks
    private HouseService houseService;

    private House house1;
    private Monster monster;
    private CursedObject cursedObject;

    @BeforeMethod
    public void prepareHouses() {
        cursedObject = new CursedObject();
        cursedObject.setName("Cake");
        cursedObject.setDescription("This is a lie.");
        cursedObject.setHouse(house1);

        monster = new Monster();
        monster.setName("GLaDOS");
        monster.setDescription("Generates lust after unreachable tasty cakes.");
        monster.setHouse(house1);

        house1 = new House();
        house1.setId(1L);
        house1.setName("Aperture");
        house1.setAddress("Aperture Science laboratories");
        house1.addMonster(monster);
        house1.addCursedObject(cursedObject);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        houseService.create(house1);
        verify(houseDao).create(house1);
    }

    @Test
    public void testUpdate() {
        when(houseDao.update(house1)).thenReturn(house1);

        House updatedHouse = houseService.update(house1);
        verify(houseDao).update(house1);

        this.assertDeepEquals(updatedHouse, house1);
    }

    @Test
    public void testDelete() {
        houseService.remove(house1);
        verify(houseDao).delete(house1);
    }

    @Test
    public void testFindById() {
        Long id = house1.getId();
        Assert.assertNotNull(id);

        when(houseDao.findById(1L)).thenReturn(house1);
        House foundHouse = houseService.findById(id);

        Assert.assertNotNull(house1);
        Assert.assertNotNull(foundHouse);
        this.assertDeepEquals(foundHouse, house1);
    }

    @Test
    public void testFindAll() {
        List<House> houses = new ArrayList<>();
        houses.add(house1);

        when(houseDao.findAll()).thenReturn(houses);
        List<House> foundHouses = houseService.findAll();

        Assert.assertNotNull(foundHouses);
        Assert.assertEquals(foundHouses.size(), 1);
        Assert.assertTrue(foundHouses.get(0).getId().equals(1L));
        Assert.assertEquals(foundHouses, houses);
    }

    /**
     * Compares the two object by their properties.
     * This method fails if one object differs from the other.
     * @param actual Actual, found object
     * @param expected Original object which the actual is derived from
     */
    private void assertDeepEquals(House actual, House expected) {
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getAddress(), expected.getAddress());
        Assert.assertEquals(actual.getMonsters(), expected.getMonsters());
        Assert.assertEquals(actual.getCursedObjects(), expected.getCursedObjects());
    }
}
