package cz.muni.fi.pa165.hauntedhouses.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Filip Petrovic (422334)
 */
@RunWith(MockitoJUnitRunner.class)
public class AbilityDaoTest {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    private AbilityDao abilityDao = new AbilityDaoImpl();
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullAbility() {
        abilityDao.create(null);
    }
}
