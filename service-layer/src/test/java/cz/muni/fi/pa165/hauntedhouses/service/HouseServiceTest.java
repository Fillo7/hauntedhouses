/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Kristyna Loukotova
 * @version 17.11.2016
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HouseServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseDao houseDao;

    @Inject
    @InjectMocks
    private HouseService houseService;

    private House house;
    private Monster monster;
    private CursedObject cursedObject;

    @BeforeMethod
    public void prepareHouses() {
        cursedObject = new CursedObject();
        monster = new Monster();
        house = new House();

        cursedObject.setName("Cake");
        cursedObject.setDescription("This is a lie.");
        //cursedObject.setHouse(house); // Not needed because of the safe setters

        monster.setName("GLaDOS");
        monster.setDescription("Generates lust after unreachable tasty cakes.");
        //monster.setHouse(house);

        house.setId(1L);
        house.setName("Aperture");
        house.setAddress("Aperture Science laboratories");
        house.addMonster(monster);
        house.addCursedObject(cursedObject);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        houseService.create(house);
        verify(houseDao, times(1)).create(house);
    }

    @Test
    public void testUpdate() {
        when(houseDao.update(house)).thenReturn(house);

        House updatedHouse = houseService.update(house);
        this.assertDeepEquals(updatedHouse, house);
    }

    @Test
    public void testDelete() {
        houseService.delete(house);
        verify(houseDao).delete(house);
    }

    @Test
    public void testGetById() {
        Long id = house.getId();
        Assert.assertNotNull(id);

        when(houseDao.getById(1L)).thenReturn(house);
        House foundHouse = houseService.getById(id);

        Assert.assertNotNull(house);
        Assert.assertNotNull(foundHouse);
        this.assertDeepEquals(foundHouse, house);
    }

    @Test
    public void testGetAll() {
        List<House> houses = new ArrayList<>();
        houses.add(house);

        when(houseDao.getAll()).thenReturn(houses);
        List<House> foundHouses = houseService.getAll();

        Assert.assertNotNull(foundHouses);
        Assert.assertEquals(foundHouses.size(), 1);
        Assert.assertTrue(foundHouses.get(0).getId().equals(1L));
        Assert.assertEquals(foundHouses, houses);
    }

    /*@Test
    public void testPurge() {
        Monster anotherMonster = new Monster();
        anotherMonster.setName("Sithis");
        house.addMonster(anotherMonster);

        when(houseDao.getById(1L)).thenReturn(house);

        Assert.assertEquals(house.getMonsters().size(), 2);
        Assert.assertEquals(house.getCursedObjects().size(), 1);

        houseService.purge(house);
        verify(houseDao).update(house);

        Assert.assertEquals(house.getMonsters().size(), 0);
        Assert.assertEquals(house.getCursedObjects().size(), 0);
    }*/

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testPurgeNull() {
        houseService.purge(null);
    }

    /*@Test
    public void testPurgeNoMonsters() {
        House house2 = new House();
        house2.setId(2L);
        house2.setName("2");
        house2.setAddress("2");
        house2.addCursedObject(cursedObject);

        Assert.assertEquals(house2.getMonsters().size(), 0);
        Assert.assertEquals(house2.getCursedObjects().size(), 1);

        when(houseDao.getById(2L)).thenReturn(house2);
        houseService.purge(house2);

        Assert.assertEquals(house2.getMonsters().size(), 0);
        Assert.assertEquals(house2.getCursedObjects().size(), 0);
    }

    @Test
    public void testPurgeNoCursedObjects() {
        House house2 = new House();
        house2.setId(2L);
        house2.setName("2");
        house2.setAddress("2");
        house2.addMonster(monster);

        Assert.assertEquals(house2.getMonsters().size(), 1);
        Assert.assertEquals(house2.getCursedObjects().size(), 0);

        when(houseDao.getById(2L)).thenReturn(house2);
        houseService.purge(house2);

        Assert.assertEquals(house2.getMonsters().size(), 0);
        Assert.assertEquals(house2.getCursedObjects().size(), 0);
    }*/

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
