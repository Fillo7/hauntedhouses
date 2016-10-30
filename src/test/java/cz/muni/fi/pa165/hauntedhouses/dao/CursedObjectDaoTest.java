package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Marek Janco
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CursedObjectDaoTest extends AbstractTestNGSpringContextTests{
    @Inject
    private CursedObjectDao coDao;
    
    @Inject
    private HouseDao houseDao;
    
    @PersistenceContext 
    private EntityManager em;
    
    private CursedObject c1;
    private CursedObject c2;
    
    private House h1;
    private House h2;
    
    @BeforeMethod
    public void before(){
        h1 = new House();
        h1.setName("house1");
        h1.setAddress("dubnica");
        houseDao.create(h1);
        
        h2 = new House();
        h2.setName("house2");
        h2.setAddress("brno");
        houseDao.create(h2);
        
        c1 = new CursedObject();
        c1.setName("doll");
        c1.setDescription("");
        c1.setHouse(h1);
        c1.setMonsterAttractionFactor(MonsterAttractionFactor.LOW);
        
        c2 = new CursedObject();
        c2.setName("necklace");
        c2.setDescription("...");
        c2.setHouse(h2);
        c2.setMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);
    }
    
    @Test
    public void testCreate(){
        coDao.addCursedObject(c1);
        
        assertNotNull(c1.getId());
        assertEquals(coDao.getCursedObject(c1.getId()).getName(), c1.getName());
        assertEquals(coDao.getCursedObject(c1.getId()).getDescription(), c1.getDescription());
        assertEquals(coDao.getCursedObject(c1.getId()).getHouse(), c1.getHouse());
        assertEquals(coDao.getCursedObject(c1.getId()).getMonsterAttractionFactor(),
                c1.getMonsterAttractionFactor());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNullCursedObject(){
        coDao.addCursedObject(null);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullName(){
        c1.setName(null);
        coDao.addCursedObject(c1);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullDescription(){
        c1.setDescription(null);
        coDao.addCursedObject(c1);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullHouse(){
        c1.setHouse(null);
        coDao.addCursedObject(c1);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullMonsterAttractionFactor(){
        c1.setMonsterAttractionFactor(null);
        coDao.addCursedObject(c1);
    }
    
    //UPDATE
    //////////////////////////////////////////////////////////////////////////
    
    @Test()
    public void testUpdate(){
        coDao.addCursedObject(c1);
        c1.setName("new name");
        c1.setDescription("new description");
        c1.setHouse(h2);
        c1.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        
        CursedObject upd =coDao.updateCursedObject(c1);
        validate(upd, c1);
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateNameToNull(){
        coDao.addCursedObject(c1);
        
        c1.setName(null);
        CursedObject upd = coDao.updateCursedObject(c1);
        em.flush();
    }
    
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateDescriptionToNull(){
        coDao.addCursedObject(c1);
        
        c1.setName(null);
        CursedObject upd = coDao.updateCursedObject(c1);
        em.flush();
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateHouseToNull(){
        coDao.addCursedObject(c1);
        
        c1.setHouse(null);
        CursedObject upd = coDao.updateCursedObject(c1);
        em.flush();
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateMonsterAttractionFactorToNull(){
        coDao.addCursedObject(c1);
        
        c1.setMonsterAttractionFactor(null);
        CursedObject upd = coDao.updateCursedObject(c1);
        em.flush();
    }
    
    //getCO
    //////////////////////////////////////////////////////////////
    @Test()
    public void testGetCursedObject(){
        coDao.addCursedObject(c1);
        coDao.addCursedObject(c2);
        
        CursedObject co = coDao.getCursedObject(c1.getId());
        validate(co, c1);
        co = coDao.getCursedObject(c2.getId());
        validate(co, c2);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetNoExistingCursedObject(){
        CursedObject co = coDao.getCursedObject(c1.getId());
    }
    
    @Test()
    public void testGetAll(){
        assertEquals(coDao.getAllCursedObjects().size(), 0);
        coDao.addCursedObject(c1);
        assertEquals(coDao.getAllCursedObjects().size(), 1);
        coDao.addCursedObject(c2);
        
        assertEquals(coDao.getAllCursedObjects().size(), 2);
        validate(coDao.getAllCursedObjects().get(0),c1);
        validate(coDao.getAllCursedObjects().get(1),c2);
    }
    
    //DELETE
    ////////////////////////////////////////////////////////////////////////
    @Test()
    public void testDeleteCursedObject(){
        coDao.addCursedObject(c1);
        coDao.addCursedObject(c2);
        assertEquals(coDao.getAllCursedObjects().size(), 2);
        
        coDao.deleteCursedObject(c1);
        assertEquals(coDao.getAllCursedObjects().size(), 1);
        validate(coDao.getAllCursedObjects().get(0),c2);
        
        coDao.deleteCursedObject(c2);
        assertEquals(coDao.getAllCursedObjects().size(), 0);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull(){
        coDao.deleteCursedObject(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNotCreatedCursedObject(){
        coDao.deleteCursedObject(c1);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateSameCursedObjects(){
        coDao.addCursedObject(c1);
        coDao.addCursedObject(c1);
    }
    
    @Test(expectedExceptions = PersistenceException.class)
    public void testCreateSameNamedCursedObjects(){
        coDao.addCursedObject(c1);
        c2.setName(c1.getName());
        coDao.addCursedObject(c2);
    }
    
    private void validate(CursedObject upd, CursedObject c1){
        assertEquals(c1.getId(), upd.getId());
        assertEquals(c1.getName(), upd.getName());
        assertEquals(c1.getDescription(), upd.getDescription());
        assertEquals(c1.getHouse(), upd.getHouse());
    }
    
}
