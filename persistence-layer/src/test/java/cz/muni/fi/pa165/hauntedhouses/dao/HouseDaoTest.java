/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Kristyna Loukotova
 * @version 31.10.2016
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HouseDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext 
    private EntityManager em;
    
    @Inject
    private HouseDao houseDao;
    
    private House houseComplete;
    private House houseMonsterOnly;
    private House houseCObjectOnly;
    
    private Monster monster;
    
    private CursedObject cursedObject;
    
    @BeforeMethod
    public void setUp() {
        houseComplete = new House();
        houseComplete.setName("White house");
        houseComplete.setAddress("1600 Pennsylvania Ave NW, Washington, DC 20500");
        
        houseMonsterOnly = new House();
        houseMonsterOnly.setName("Prague castle");
        houseMonsterOnly.setAddress("Prague 1, 119 08");
        
        houseCObjectOnly = new House();
        houseCObjectOnly.setName("Kremlin");
        houseCObjectOnly.setAddress("Moscow, 103073");
        
        monster = new Monster();
        monster.setName("Spaghetti flying monster");
        monster.setDescription("Strangles the victim with pasta tentacles");
        
        cursedObject = new CursedObject();
        cursedObject.setName("The roll of doom");
        cursedObject.setDescription("Empty roll of toilet paper");
        cursedObject.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        
        // Both monster and cursed object
        houseComplete.addMonster(monster);
        houseComplete.addCursedObject(cursedObject);
        monster.setHouse(houseComplete);
        cursedObject.setHouse(houseComplete);
        
        // Only monster
        houseMonsterOnly.addMonster(monster);
        monster.setHouse(houseMonsterOnly);
        
        // Only cursed object
        houseCObjectOnly.addCursedObject(cursedObject);
        cursedObject.setHouse(houseCObjectOnly);
    }

    /**
     * Test of create method, of class HouseDao.
     */
    @Test
    public void testCreate() {
        houseDao.create(houseComplete);
        
        Assert.assertNotNull(houseComplete.getId());
        House foundHouse = houseDao.findById(houseComplete.getId());
        this.assertDeepEquals(foundHouse, houseComplete);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        houseDao.create(null);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateWithNullName() {
        houseComplete.setName(null);
        houseDao.create(houseComplete);
    }
    
    // org.hibernate.exception.ConstraintViolationException doesn't seem to be recognized here
    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateWithDuplicitName() {
        houseComplete.setName("Yolo");
        houseDao.create(houseComplete);
        
        houseMonsterOnly.setName("Yolo");
        houseDao.create(houseMonsterOnly);
        
        Assert.assertEquals(houseDao.findAll().size(), 1);
    }

    /**
     * Test of update method, of class HouseDao.
     */
    @Test
    public void testUpdate() {
        String testedHouseName = "Oasis of calm";
        String testedHouseAddress = "Some random, quiet forrest";
        String testedMonsterName = "Soft fluffy sheep";
        String testedCObjectName = "Chocolate fountain";
        
        houseDao.create(houseComplete);
        houseComplete.setName(testedHouseName);
        houseComplete.setAddress(testedHouseAddress);
        
        houseComplete.removeMonster(monster);
        Monster newMonster = new Monster();
        newMonster.setName(testedMonsterName);
        houseComplete.addMonster(newMonster);
        
        houseComplete.removeCursedObject(cursedObject);
        CursedObject newCursedObject = new CursedObject();
        newCursedObject.setName(testedCObjectName);
        houseComplete.addCursedObject(newCursedObject);
        
        House updatedHouse = houseDao.update(houseComplete);

        Assert.assertEquals(updatedHouse.getName(), testedHouseName);
        Assert.assertEquals(updatedHouse.getAddress(), testedHouseAddress);
        this.assertDeepEquals(updatedHouse, houseComplete);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        houseDao.update(null);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testUpdateWithNullName() {
        houseDao.create(houseComplete);
        houseComplete.setName(null);
        houseDao.update(houseComplete);
        em.flush();
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testUpdateWithNullAddress() {
        houseDao.create(houseComplete);
        houseComplete.setAddress(null);
        houseDao.update(houseComplete);
        em.flush();
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testUpdateWithDuplicitName() {
        houseDao.create(houseComplete);
        houseDao.create(houseMonsterOnly);
        
        houseComplete.setName("Yolo");
        houseMonsterOnly.setName("Yolo");
        
        houseDao.update(houseComplete);
        em.flush();
        
        houseDao.update(houseMonsterOnly);
        em.flush();
    }

    /**
     * Test of delete method, of class HouseDao.
     */
    @Test
    public void testRemove() {
        houseDao.create(houseComplete);
        houseDao.create(houseMonsterOnly);
        
        Assert.assertEquals(houseDao.findAll().size(), 2);
        Assert.assertNotNull(houseDao.findById(houseComplete.getId()));
        Assert.assertNotNull(houseDao.findById(houseMonsterOnly.getId()));
        
        houseDao.delete(houseComplete);
        Assert.assertEquals(houseDao.findAll().size(), 1);
        
        houseDao.delete(houseMonsterOnly);
        Assert.assertEquals(houseDao.findAll().size(), 0);
        
        Assert.assertNull(houseDao.findById(houseComplete.getId()));
        Assert.assertNull(houseDao.findById(houseMonsterOnly.getId()));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveNonexistent() {
        Assert.assertFalse(houseDao.findAll().contains(houseComplete));
        houseDao.delete(houseComplete);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveNull() {
        houseDao.delete(null);
    }

    /**
     * Test of findById method, of class HouseDao.
     */
    @Test
    public void testFindById() {
        houseDao.create(houseComplete);
        houseDao.create(houseMonsterOnly);
        
        House foundHouse = houseDao.findById(houseComplete.getId());
        this.assertDeepEquals(foundHouse, houseComplete);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNonexistent() {
        Assert.assertFalse(houseDao.findAll().contains(houseComplete));
        houseDao.findById(houseComplete.getId());
    }

    /**
     * Test of findAll method, of class HouseDao.
     */
    @Test
    public void testFindAll() {
        houseDao.create(houseComplete);
        houseDao.create(houseMonsterOnly);
        houseDao.create(houseCObjectOnly);
        
        List<House> houses = houseDao.findAll();
        Assert.assertEquals(houses.size(), 3);
        Assert.assertTrue(houses.contains(houseComplete));
        Assert.assertTrue(houses.contains(houseMonsterOnly));
        Assert.assertTrue(houses.contains(houseCObjectOnly));
    }

    @Test
    public void testFindByName() {
        houseDao.create(houseComplete);
        houseDao.create(houseMonsterOnly);
        houseDao.create(houseCObjectOnly);

        House result = houseDao.findByName("Kremlin");
        Assert.assertNotNull(result);
        assertDeepEquals(result, houseCObjectOnly);
    }
    
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testModifyMonsters() {
        houseDao.create(houseMonsterOnly);
        
        Set<Monster> monsters = houseDao.findById(houseMonsterOnly.getId()).getMonsters();
        monsters.add(new Monster());
    }
    
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testModifyCursedObjects() {
        houseDao.create(houseCObjectOnly);
        
        Set<CursedObject> cursedObjects = houseDao.findById(houseCObjectOnly.getId()).getCursedObjects();
        cursedObjects.add(new CursedObject());
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
