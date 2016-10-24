/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.entity.House;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristyna Loukotova
 * @version 25.10.2016
 */
public class HouseDaoTest {
    
    @PersistenceUnit
    EntityManagerFactory emf;
    
    public HouseDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class HouseDao.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        House house = new House();
        HouseDao houseDao = new HouseDaoImpl();
        houseDao.create(house);
        System.out.println(house == null ? "house is null" : "house is not null");
        
        System.out.println(emf == null ? "emf is null" : "emf is not null");

        EntityManager e = emf.createEntityManager();
        System.out.println(e == null ? "Entity manager null" : "Entity manager not null");
        House result = e.find(House.class, house);
        System.out.println(result == null ? "Result null" : "Result not null");
        
        assertNotNull(result);
        assertEquals(house, result);
    }

    /**
     * Test of update method, of class HouseDao.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        House house = null;
        HouseDao instance = new HouseDaoImpl();
        House expResult = null;
        House result = instance.update(house);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class HouseDao.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        House house = null;
        HouseDao instance = new HouseDaoImpl();
        instance.remove(house);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class HouseDao.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        Long id = null;
        HouseDao instance = new HouseDaoImpl();
        House expResult = null;
        House result = instance.findById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class HouseDao.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        HouseDao instance = new HouseDaoImpl();
        List<House> expResult = null;
        List<House> result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findByName method, of class HouseDao.
     */
    @Test
    public void testFindByName() {
        System.out.println("findByName");
        String name = "";
        HouseDao instance = new HouseDaoImpl();
        House expResult = null;
        House result = instance.findByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class HouseDaoImpl implements HouseDao {

        public void create(House house) {
        }

        public House update(House house) {
            return null;
        }

        public void remove(House house) {
        }

        public House findById(Long id) {
            return null;
        }

        public List<House> findAll() {
            return null;
        }

        public House findByName(String name) {
            return null;
        }
    }
    
}
