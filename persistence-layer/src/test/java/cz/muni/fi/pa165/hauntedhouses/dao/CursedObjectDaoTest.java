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

import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

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
        coDao.create(c1);
        
        assertNotNull(c1.getId());
        assertEquals(coDao.getById(c1.getId()).getName(), c1.getName());
        assertEquals(coDao.getById(c1.getId()).getDescription(), c1.getDescription());
        assertEquals(coDao.getById(c1.getId()).getHouse(), c1.getHouse());
        assertEquals(coDao.getById(c1.getId()).getMonsterAttractionFactor(),
                c1.getMonsterAttractionFactor());
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullCursedObject(){
        coDao.create(null);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullName(){
        c1.setName(null);
        coDao.create(c1);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullDescription(){
        c1.setDescription(null);
        coDao.create(c1);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullHouse(){
        c1.setHouse(null);
        coDao.create(c1);
    }
    
    @Test(expectedExceptions = ValidationException.class)
    public void testCreateCursedObjectWithNullMonsterAttractionFactor(){
        c1.setMonsterAttractionFactor(null);
        coDao.create(c1);
    }
    
    @Test()
    public void testUpdate(){
        coDao.create(c1);
        c1.setName("new name");
        c1.setDescription("new description");
        c1.setHouse(h2);
        c1.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        
        CursedObject upd =coDao.update(c1);
        validate(upd, c1);
        
        c1.setName("old name");
        upd =coDao.update(c1);
        validate(upd, c1);
        
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateNameToNull(){
        coDao.create(c1);
        
        c1.setName(null);
        CursedObject upd = coDao.update(c1);
        em.flush();
    }
    
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateDescriptionToNull(){
        coDao.create(c1);
        
        c1.setName(null);
        CursedObject upd = coDao.update(c1);
        em.flush();
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateHouseToNull(){
        coDao.create(c1);
        
        c1.setHouse(null);
        CursedObject upd = coDao.update(c1);
        em.flush();
    }
    
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testUpdateMonsterAttractionFactorToNull(){
        coDao.create(c1);
        
        c1.setMonsterAttractionFactor(null);
        CursedObject upd = coDao.update(c1);
        em.flush();
    }
   
    @Test(expectedExceptions = PersistenceException.class)
    public void testUpdateWithSameName(){
        coDao.create(c1);
        coDao.create(c2);
        
        c1.setName("NAME");
        c2.setName("NAME");
        
        coDao.update(c1);
        em.flush();
        coDao.update(c2);
        em.flush();
    }
   
    @Test()
    public void testGetCursedObject(){
        coDao.create(c1);
        coDao.create(c2);
        
        CursedObject co = coDao.getById(c1.getId());
        validate(co, c1);
        co = coDao.getById(c2.getId());
        validate(co, c2);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void testGetNoExistingCursedObject(){
        assertFalse(coDao.getAll().contains(c1));
        CursedObject co = coDao.getById(c1.getId());
    }
    
    @Test()
    public void testGetAll(){
        assertEquals(coDao.getAll().size(), 0);
        coDao.create(c1);
        assertEquals(coDao.getAll().size(), 1);
        coDao.create(c2);
        
        assertEquals(coDao.getAll().size(), 2);
        validate(coDao.getAll().get(0),c1);
        validate(coDao.getAll().get(1),c2);
    }
    
    @Test()
    public void testDeleteCursedObject(){
        coDao.create(c1);
        coDao.create(c2);
        assertEquals(coDao.getAll().size(), 2);
        
        coDao.delete(c1);
        assertEquals(coDao.getAll().size(), 1);
        validate(coDao.getAll().get(0),c2);
        
        coDao.delete(c2);
        assertEquals(coDao.getAll().size(), 0);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNull(){
        coDao.delete(null);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNotCreatedCursedObject(){
        coDao.delete(c1);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateSameNamedCursedObjects(){
        coDao.create(c1);
        c2.setName(c1.getName());
        coDao.create(c2);
    }
    
    
    private void validate(CursedObject upd, CursedObject c1){
        assertEquals(upd, c1);
        assertEquals(c1.getId(), upd.getId());
        assertEquals(c1.getName(), upd.getName());
        assertEquals(c1.getDescription(), upd.getDescription());
        assertEquals(c1.getHouse(), upd.getHouse());
    }
    
}
