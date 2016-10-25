package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

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

        Calendar startCalCat = Calendar.getInstance();
        startCalCat.set(Calendar.HOUR_OF_DAY, 10);
        startCalCat.set(Calendar.MINUTE, 15);

        Calendar endCalCat = Calendar.getInstance();
        endCalCat.set(Calendar.HOUR_OF_DAY, 15);
        endCalCat.set(Calendar.MINUTE, 45);

        cat = new Monster();
        cat.setHauntedIntervalStart(startCalCat.getTime());
        cat.setHauntedIntervalEnd(endCalCat.getTime());
        cat.setName("Cicka");
        cat.setDescription("Cicka Micka zlatunka");

        Calendar startCaldogWithNullName = Calendar.getInstance();
        startCaldogWithNullName.set(Calendar.HOUR_OF_DAY, 17);
        startCaldogWithNullName.set(Calendar.MINUTE, 41);

        Calendar endCaldogWithNullName = Calendar.getInstance();
        endCaldogWithNullName.set(Calendar.HOUR_OF_DAY, 19);
        endCaldogWithNullName.set(Calendar.MINUTE, 18);

        dogWithNullName = new Monster();
        dogWithNullName.setHauntedIntervalStart(startCalCat.getTime());
        dogWithNullName.setHauntedIntervalEnd(endCalCat.getTime());
        dogWithNullName.setDescription("k nohe psisko");

        dogWithNullDesc= new Monster();
        dogWithNullDesc.setHauntedIntervalStart(startCalCat.getTime());
        dogWithNullDesc.setHauntedIntervalEnd(endCalCat.getTime());
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