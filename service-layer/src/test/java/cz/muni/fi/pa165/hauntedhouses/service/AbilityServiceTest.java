package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AbilityServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private AbilityDao abilityDao;

    @Inject
    @InjectMocks
    private AbilityService abilityService;

    private Ability one;
    private Ability two;

    private Monster monster;

    @BeforeMethod
    public void setAbilities() {
        monster = new Monster();
        one = new Ability();
        two = new Ability();

        monster.setName("Doppelganger");
        monster.setDescription("Its a doppelganger.");
        monster.addAbility(one);
        monster.addAbility(two);

        one.setId(0L);
        one.setName("Shameless copy");
        one.setDescription("Shamelessly copies what its target is doing.");
        one.addMonster(monster);

        two.setId(1L);
        two.setName("Charm");
        two.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
        two.addMonster(monster);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        abilityService.create(one);

        verify(abilityDao, times(1)).create(one);
    }

    @Test
    public void testUpdate() {
        when(abilityDao.update(one)).thenReturn(one);

        Ability updated = abilityService.update(one);
        assertDeepEquals(updated, one);
    }

    @Test
    public void testRemove() {
        abilityService.remove(one);

        verify(abilityDao, times(1)).delete(one);
    }

    @Test
    public void testFindById() {
        when(abilityDao.findById(one.getId())).thenReturn(one);

        Ability retrieved = abilityService.findById(one.getId());
        assertDeepEquals(retrieved, one);
    }

    @Test
    public void testFindByName() {
        when(abilityDao.findByName(one.getName())).thenReturn(one);

        Ability retrieved = abilityService.findByName(one.getName());
        assertDeepEquals(retrieved, one);
    }

    @Test
    public void testFindAll() {
        List<Ability> list = new ArrayList<>();
        list.add(one);
        list.add(two);

        when(abilityDao.findAll()).thenReturn(list);

        List<Ability> retrieved = abilityService.findAll();
        Assert.assertEquals(retrieved.size(), 2);
        Assert.assertTrue(retrieved.get(0).getId().equals(0L));
        Assert.assertTrue(retrieved.get(1).getId().equals(1L));
    }

    private void assertDeepEquals(Ability actual, Ability expected) {
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        Assert.assertEquals(actual.getMonsters(), expected.getMonsters());
    }
}
