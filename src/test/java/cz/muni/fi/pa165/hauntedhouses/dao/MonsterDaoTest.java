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

    @BeforeMethod
    public void setMonsters(){
        h1 = new House();
        h1.setName("Vila u Vila Rozborila");
        h1.setAddress("Koliba, Bratislava");

        LocalTime startCat = LocalTime.of(10, 15, 24);
        LocalTime endCat = LocalTime.of(15, 24, 2);

        cat = new Monster();
        cat.setHauntedIntervalStart(startCat);
        cat.setHauntedIntervalEnd(endCat);
        cat.setName("Cicka");
        cat.setDescription("Cicka Micka zlatunka");

        LocalTime startDogNullName = LocalTime.of(17, 41, 0);
        LocalTime endDogNullName = LocalTime.of(19, 18, 2);

        dogWithNullName = new Monster();
        dogWithNullName.setHauntedIntervalStart(startDogNullName);
        dogWithNullName.setHauntedIntervalEnd(endDogNullName);
        dogWithNullName.setDescription("k nohe psisko");

        dogWithNullDesc= new Monster();
        dogWithNullDesc.setHauntedIntervalStart(startCat);
        dogWithNullDesc.setHauntedIntervalEnd(endCat);
        dogWithNullDesc.setName("Hafko");

    }

    @Test
    public void createMonsterTest() {
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createMonsterNullTest(){
        monsterDao.create(null);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterNameNullTest(){
        monsterDao.create(dogWithNullName);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void createMonsterDescriptionNullTest(){
        monsterDao.create(dogWithNullDesc);
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