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
import javax.validation.constraints.NotNull;
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
    private Monster dog;

    @BeforeMethod
    public void setMonsters(){
        h1 = new House();
        h1.setName("Vila u Vila Rozborila");
        h1.setAddress("Koliba, Bratislava");
//        houseDao.create(h1);

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
//        monsterDao.create(cat);

        Calendar startCalDog = Calendar.getInstance();
        startCalDog.set(Calendar.HOUR_OF_DAY, 17);
        startCalDog.set(Calendar.MINUTE, 41);

        Calendar endCalDog = Calendar.getInstance();
        endCalDog.set(Calendar.HOUR_OF_DAY, 19);
        endCalDog.set(Calendar.MINUTE, 18);

        dog = new Monster();
        dog.setHauntedIntervalStart(startCalCat.getTime());
        dog.setHauntedIntervalEnd(endCalCat.getTime());
        dog.setName("Porsche");
        dog.setDescription("k nohe psisko");
//        monsterDao.create(dog);
    }

//    @BeforeMethod
//    public void setRelations(){
//        House myHouse = houseDao.findAll().get(0);
//        myHouse.addMonster(monsterDao.findAll().get(0));
//        houseDao.update(myHouse);
//    }

    @Test
    public void testCreateMonster() {
        monsterDao.create(cat);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void testCreateMonsterNull(){
        monsterDao.create(null);
    }

}