package cz.muni.fi.pa165.hountedhouses.dao;

import cz.muni.fi.pa165.hountedhouses.PersistenceApplicationContext;
import cz.muni.fi.pa165.hountedhouses.entity.House;
import cz.muni.fi.pa165.hountedhouses.entity.Monster;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Ondro on 20-Oct-16.
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MonsterDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private MonsterDao monsterDao;

    @PersistenceContext
    private EntityManager entityManager;

}
