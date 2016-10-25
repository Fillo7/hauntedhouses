package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.time.LocalTime;

/**
 * Created by Ondro on 20-Oct-16.
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MonsterDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private HouseDao houseDao;

    @Inject
    private MonsterDao monsterDao;

    private House h1;
    private Monster cat;
    private Monster dogWithNullName;
    private Monster dogWithNullDesc;
    private Monster dogWithNullStartTime;
    private Monster dogWithNullEndTime;

    @BeforeMethod
    public void setMonsters(){
        h1 = new House();
        h1.setName("Vila u Vila Rozborila");
        h1.setAddress("Koliba, Bratislava");

        cat = new Monster();
        cat.setHauntedIntervalStart(LocalTime.of(10, 15, 24));
        cat.setHauntedIntervalEnd(LocalTime.of(15, 24, 2));
        cat.setName("Cicka");
        cat.setDescription("Cicka Micka zlatunka");

        dogWithNullName = new Monster();
        dogWithNullName.setHauntedIntervalStart(LocalTime.of(17, 41, 0));
        dogWithNullName.setHauntedIntervalEnd(LocalTime.of(19, 18, 2));
        dogWithNullName.setDescription("k nohe psisko");

        dogWithNullDesc= new Monster();
        dogWithNullDesc.setHauntedIntervalStart(LocalTime.of(10, 15, 24));
        dogWithNullDesc.setHauntedIntervalEnd(LocalTime.of(15, 24, 2));
        dogWithNullDesc.setName("Hafko");

        dogWithNullStartTime= new Monster();
        dogWithNullStartTime.setHauntedIntervalEnd(LocalTime.of(15, 24, 2));
        dogWithNullStartTime.setName("Porsche");
        dogWithNullStartTime.setDescription("tichy psik");

        dogWithNullEndTime= new Monster();
        dogWithNullEndTime.setHauntedIntervalStart(LocalTime.of(15, 24, 2));
        dogWithNullEndTime.setName("Mazda");
        dogWithNullEndTime.setDescription("hlucny psik");
    }

    @Test
    public void createMonsterTest() {
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createMonsterNullTest(){
        monsterDao.create(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateMonsterNullTest(){
        monsterDao.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeMonsterNullTest(){
        monsterDao.delete(null);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterNameNullTest(){
        monsterDao.create(dogWithNullName);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterDescriptionNullTest(){
        monsterDao.create(dogWithNullDesc);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterStartTimeNullTest(){
        monsterDao.create(dogWithNullStartTime);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterEndTimeNullTest(){
        monsterDao.create(dogWithNullEndTime);
    }

    @Test
    public void testUpdateName(){
        monsterDao.create(cat);
        cat.setName("NovaCica");
        Monster updated = monsterDao.update(cat);
        Assert.assertEquals(updated.getName(), "NovaCica");
        assertDeepEquals(updated, cat);
    }

    private void assertDeepEquals(Monster m1, Monster m2){
        Assert.assertEquals(m1, m2);
        Assert.assertEquals(m1.getId(), m2.getId());
        Assert.assertEquals(m1.getName(), m2.getName());
        Assert.assertEquals(m1.getDescription(), m2.getDescription());
        Assert.assertEquals(m1.getHauntedIntervalEnd(), m2.getHauntedIntervalEnd());
        Assert.assertEquals(m1.getHauntedIntervalStart(), m2.getHauntedIntervalStart());
        Assert.assertEquals(m1.getHouse(), m2.getHouse());
        Assert.assertEquals(m1.getAbilities(), m2.getAbilities());
    }
}