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
        
        Assert.assertEquals(abilityDao.findById(abilityOne.getId()).getName(), abilityOne.getName());
        Assert.assertEquals(abilityDao.findById(abilityOne.getId()).getDescription(), abilityOne.getDescription());
        Assert.assertEquals(abilityDao.findById(abilityOne.getId()).getMonsters(), abilityOne.getMonsters());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        Ability ability = null;
        abilityDao.create(ability);		
    }
    
    @Test
    public void update() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);
        
        abilityThree.setName("Improved charm");
        abilityDao.update(abilityThree);
        
        Ability found = abilityDao.findById(abilityThree.getId());
        Assert.assertEquals(found.getName(), abilityThree.getName());	
    }
    
    @Test
    public void testDelete() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);
        
        Assert.assertNotNull(abilityDao.findById(abilityOne.getId()));
        abilityDao.delete(abilityOne);
        Assert.assertNull(abilityDao.findById(abilityOne.getId()));
    }
    
    @Test
    public void testFindById() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityTwo);
        
        Ability found = abilityDao.findById(abilityOne.getId());
        Assert.assertEquals(found.getName(), abilityOne.getName());
        Assert.assertEquals(found.getDescription(), abilityOne.getDescription());
        Assert.assertEquals(found.getMonsters(), abilityOne.getMonsters());
    }
    
    @Test
    public void testFindByName() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);
        
        Ability found = abilityDao.findByName(abilityOne.getName());
        Assert.assertEquals(found.getName(), abilityOne.getName());
        Assert.assertEquals(found.getDescription(), abilityOne.getDescription());
        Assert.assertEquals(found.getMonsters(), abilityOne.getMonsters());
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
}