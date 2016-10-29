package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes=PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class AbilityDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private AbilityDao abilityDao;
    
    @Inject
    private MonsterDao monsterDao;
    
    @PersistenceContext 
    private EntityManager entityManager;
    
    private Ability abilityOne;
    private Ability abilityTwo;
    private Ability abilityThree;
    
    private Monster monster;
    
    @BeforeMethod
    public void setAbilities() {
        monster = new Monster();
        monster.setName("Unicorn");
        monster.setDescription("Leaves rainbows in its wake.");
        
        abilityOne = new Ability();
        abilityOne.setName("Impale");
        abilityOne.setDescription("Impales an enemy with its glorious horn.");

        abilityTwo = new Ability();
        abilityTwo.setName("Spawn rainbows");
        abilityTwo.setDescription("Spawns rainbows all around the place, blinding anyone who dares to wander nearby.");
        
        abilityThree = new Ability();
        abilityThree.setName("Charm");
        abilityThree.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
        
        abilityOne.addMonster(monster);
        abilityTwo.addMonster(monster);
        abilityThree.addMonster(monster);
        
        monster.addAbility(abilityOne);
        monster.addAbility(abilityTwo);
        monster.addAbility(abilityThree);
    }
    
    @Test
    public void testCreate() {
        abilityDao.create(abilityOne);
        Assert.assertEquals(abilityDao.findById(abilityOne.getId()).getName(), "Impale");
        Assert.assertEquals(abilityDao.findById(abilityOne.getId()).getDescription(), "Impales an enemy with its glorious horn.");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        Ability ability = null;
        abilityDao.create(ability);		
    }
    
    @Test
    public void testFindAll() {	
        abilityDao.create(abilityOne);
	abilityDao.create(abilityTwo);

        List<Ability> abilities = abilityDao.findAll();
		
        Assert.assertEquals(abilities.size(), 2);
        
        Ability assertFirst = new Ability();
        Ability assertSecond = new Ability();
        assertFirst.setName("Impale");
        assertFirst.setDescription("Impales an enemy with its glorious horn.");
        assertFirst.addMonster(monster);
        assertSecond.setName("Spawn rainbows");
        assertSecond.setDescription("Spawns rainbows all around the place, blinding anyone who dares to wander nearby.");
        assertSecond.addMonster(monster);
		
        Assert.assertTrue(abilities.contains(assertFirst));
        Assert.assertTrue(abilities.contains(assertSecond));	
    }
	
    @Test
    public void testDelete() {
        abilityDao.create(abilityOne);
        Assert.assertNotNull(abilityDao.findById(abilityOne.getId()));
        abilityDao.delete(abilityOne);
        Assert.assertNull(abilityDao.findById(abilityOne.getId()));
    }
}