package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
public class AbilityDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private AbilityDao abilityDao;
    
    @Inject
    private MonsterDao monsterDao;
    
    @PersistenceContext 
    private EntityManager entityManager;
    
    @Test
    public void testCreate() {
        Ability ability = new Ability();
        ability.setName("Freeze brain");
        ability.setDescription("Freezes brain.");
        
        abilityDao.create(ability);
        Assert.assertEquals(abilityDao.findById(ability.getId()).getName(), "Freeze brain");
        Assert.assertEquals(abilityDao.findById(ability.getId()).getDescription(), "Freezes brain.");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        Ability ability = null;
        abilityDao.create(ability);		
    }
    
    @Test
    public void testFindAll() {
        Ability first = new Ability();
        Ability second = new Ability();
	
        first.setName("Freeze brain");
        first.setDescription("Freezes brain.");
	second.setName("Ignite hands");
        second.setDescription("Ignites hands.");
		
        abilityDao.create(first);
	abilityDao.create(second);

        List<Ability> abilities = abilityDao.findAll();
		
        Assert.assertEquals(abilities.size(), 2);
        
        Ability assertFirst = new Ability();
        Ability assertSecond = new Ability();
        assertFirst.setName("Freeze brain");
        assertFirst.setDescription("Freezes brain.");
        assertSecond.setName("Ignite hands");
        assertSecond.setDescription("Ignites hands.");
		
        Assert.assertTrue(abilities.contains(assertFirst));
        Assert.assertTrue(abilities.contains(assertSecond));	
    }
	
    @Test()
    public void delete() {
        Ability ability = new Ability();
        ability.setName("Freeze brain");
        ability.setDescription("Freezes brain.");
        
        abilityDao.create(ability);
        Assert.assertNotNull(abilityDao.findById(ability.getId()));
        abilityDao.delete(ability);
        Assert.assertNull(abilityDao.findById(ability.getId()));
    }
}