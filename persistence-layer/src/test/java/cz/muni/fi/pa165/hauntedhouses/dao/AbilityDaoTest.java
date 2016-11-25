package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
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

        Assert.assertEquals(abilityDao.getById(abilityOne.getId()).getName(), abilityOne.getName());
        Assert.assertEquals(abilityDao.getById(abilityOne.getId()).getDescription(), abilityOne.getDescription());
        Assert.assertEquals(abilityDao.getById(abilityOne.getId()).getMonsters(), abilityOne.getMonsters());
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateNullAbility() {
        Ability ability = null;
        abilityDao.create(ability);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateNullNameAbility() {
        abilityOne.setName(null);
        abilityDao.create(abilityOne);
    }

    @Test
    public void testUpdate() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);

        abilityThree.setName("Improved charm");
        abilityDao.update(abilityThree);

        Ability found = abilityDao.getById(abilityThree.getId());
        Assert.assertEquals(found.getName(), abilityThree.getName());
    }

    @Test
    public void testDelete() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);

        Assert.assertNotNull(abilityDao.getById(abilityOne.getId()));
        abilityDao.delete(abilityOne);
        Assert.assertNull(abilityDao.getById(abilityOne.getId()));
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testDeleteNonExisting() {
        abilityDao.delete(abilityOne);
    }

    @Test
    public void testGetById() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityTwo);

        Ability found = abilityDao.getById(abilityOne.getId());
        Assert.assertEquals(found.getName(), abilityOne.getName());
        Assert.assertEquals(found.getDescription(), abilityOne.getDescription());
        Assert.assertEquals(found.getMonsters(), abilityOne.getMonsters());
    }

    @Test
    public void testGetByName() {
        abilityDao.create(abilityOne);
        abilityDao.create(abilityThree);

        Ability found = abilityDao.getByName(abilityOne.getName());
        Assert.assertEquals(found.getName(), abilityOne.getName());
        Assert.assertEquals(found.getDescription(), abilityOne.getDescription());
        Assert.assertEquals(found.getMonsters(), abilityOne.getMonsters());
    }

    @Test
    public void testGetAll() {
        abilityDao.create(abilityOne);
	abilityDao.create(abilityTwo);
        abilityDao.create(abilityThree);

        List<Ability> abilities = abilityDao.getAll();

        Assert.assertEquals(abilities.size(), 3);

        Ability assertFirst = new Ability();
        Ability assertSecond = new Ability();
        Ability assertThird = new Ability();

        assertFirst.setName("Impale");
        assertFirst.setDescription("Impales an enemy with its glorious horn.");
        assertFirst.addMonster(monster);

        assertSecond.setName("Spawn rainbows");
        assertSecond.setDescription("Spawns rainbows all around the place, blinding anyone who dares to wander nearby.");
        assertSecond.addMonster(monster);

        assertThird.setName("Charm");
        assertThird.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
        assertThird.addMonster(monster);

        Assert.assertTrue(abilities.contains(assertFirst));
        Assert.assertTrue(abilities.contains(assertSecond));
        Assert.assertTrue(abilities.contains(assertThird));
    }
}
