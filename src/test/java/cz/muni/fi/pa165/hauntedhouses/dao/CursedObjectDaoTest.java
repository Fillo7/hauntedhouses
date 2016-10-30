package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
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
public class CursedObjectDaoTest {
    @Inject
    private CursedObjectDao coDao;
    
    @PersistenceContext 
    private EntityManager em;
    
    private CursedObject c1;
    private CursedObject c2;
    private CursedObject c3;
    
    private House h1;
    private House h2;
    
    @BeforeMethod
    public void before(){
        h1 = new House();
        h1.setName("house1");
        h1.setAddress("dubnica");
        
        h2 = new House();
        h2.setName("house2");
        h2.setAddress("brno");
        
        c1 = new CursedObject();
        c1.setName("doll");
        c1.setDescription("");
        c1.setHouse(h1);
        c1.setMonsterAttractionFactor(MonsterAttractionFactor.LOW);
        
        c2 = new CursedObject();
        c2.setName("necklace");
        c2.setDescription("...");
        c2.setHouse(h1);
        c2.setMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);
        
        c3 = new CursedObject();
        c3.setName("bracelet");
        c3.setDescription("no description");
        c3.setHouse(h2);
        c3.setMonsterAttractionFactor(MonsterAttractionFactor.HIGH);
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
        
    
}
