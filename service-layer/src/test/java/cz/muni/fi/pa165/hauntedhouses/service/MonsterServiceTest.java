
package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import org.testng.Assert;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Ondro's imports
/*
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.time.LocalTime;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslateAspect;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.util.ReflectionTestUtils;
import static org.testng.Assert.assertEquals;
*/

// Kristyna's imports
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;

/**
 *
 * @author Kristyna Loukotova
 * @version 24.11.2016
=======*/

/**
 * Test class for MonsterService
 *
 * Created by Ondrej Oravcok on 23-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MonsterServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MonsterDao monsterDao;

    @Inject
    @InjectMocks
    private MonsterService monsterService;

    private Monster monster;
    private House house;
    private Ability ability;

    @BeforeMethod
    public void prepareHouses() {
        monster = new Monster();
        house = new House();
        ability = new Ability();

        monster.setId(1L);
        monster.setName("Ulfric Stormcloak");
        monster.setDescription("Skyrim belongs to the nords!");
        monster.setHouse(house);
        monster.addAbility(ability);

        house.setId(1L);
        house.setName("Palace of the kings");
        house.setAddress("Windhelm");

        ability.setId(1L);
        ability.setName("Shouting");
        ability.setDescription("Shouting people to death, especially the kings");
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        monsterService.create(monster);
        verify(monsterDao, times(1)).create(monster);
    }

    @Test
    public void testUpdate() {
        when(monsterDao.update(monster)).thenReturn(monster);

        Monster updatedMonster = monsterService.update(monster);
        this.assertDeepEquals(updatedMonster, monster);
    }

    @Test
    public void testDelete() {
        monsterService.delete(monster);
        verify(monsterDao).delete(monster);
    }

    @Test
    public void testGetById() {
        Long id = monster.getId();
        Assert.assertNotNull(id);

        when(monsterDao.getById(1L)).thenReturn(monster);
        Monster foundMonster = monsterService.getById(id);

        Assert.assertNotNull(monster);
        Assert.assertNotNull(foundMonster);
        this.assertDeepEquals(foundMonster, monster);
    }

    @Test
    public void testGetAll() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster);

        when(monsterDao.getAll()).thenReturn(monsters);
        List<Monster> foundMonsters = monsterService.getAll();

        Assert.assertNotNull(foundMonsters);
        Assert.assertEquals(foundMonsters.size(), 1);
        Assert.assertTrue(foundMonsters.get(0).getId().equals(1L));
        Assert.assertEquals(foundMonsters, monsters);
    }

    @Test
    public void testMoveToAnotherHouse() {
        House newHouse = new House();
        newHouse.setId(2L);
        newHouse.setName("Blue palace");
        newHouse.setAddress("Solitude");

        Assert.assertNotEquals(monster.getHouse(), newHouse);
        Assert.assertEquals(monster.getHouse(), house);
        Assert.assertEquals(newHouse.getMonsters().size(), 0);
        Assert.assertEquals(house.getMonsters().size(), 1);

        when(monsterDao.getById(1L)).thenReturn(monster);
        monsterService.moveToAnotherHouse(monster, newHouse);

        Assert.assertEquals(monster.getHouse(), newHouse);
        Assert.assertNotEquals(monster.getHouse(), house);
        Assert.assertEquals(newHouse.getMonsters().size(), 1);
        Assert.assertEquals(house.getMonsters().size(), 0);
    }

    @Test
    public void testMoveToAnotherHouseTheSame() {
        Assert.assertEquals(monster.getHouse(), house);
        Assert.assertEquals(house.getMonsters().size(), 1);

        when(monsterDao.getById(1L)).thenReturn(monster);
        monsterService.moveToAnotherHouse(monster, house);

        Assert.assertEquals(monster.getHouse(), house);
        Assert.assertEquals(house.getMonsters().size(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveToAnotherHouseNullMonster() {
        Assert.assertEquals(monster.getHouse(), house);
        Assert.assertEquals(house.getMonsters().size(), 1);

        monsterService.moveToAnotherHouse(null, house);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMoveToAnotherHouseNullHouse() {
        Assert.assertEquals(monster.getHouse(), house);
        Assert.assertEquals(house.getMonsters().size(), 1);

        monsterService.moveToAnotherHouse(monster, null);
    }

    /**
     * Compares the two object by their properties.
     * This method fails if one object differs from the other.
     * @param actual Actual, found object
     * @param expected Original object which the actual is derived from
     */
    private void assertDeepEquals(Monster actual, Monster expected) {
        Assert.assertEquals(actual, expected);
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        Assert.assertEquals(actual.getHauntedIntervalStart(), expected.getHauntedIntervalStart());
        Assert.assertEquals(actual.getHauntedIntervalEnd(), expected.getHauntedIntervalEnd());
        Assert.assertEquals(actual.getHouse(), expected.getHouse());

        /*System.out.println("Actual: " + actual.getAbilities().size() + ", expected: " + expected.getAbilities().size());
        System.out.println("Actual: " + actual.toString());
        System.out.println("Expected: " + expected.toString());
        actual.getAbilities().forEach(abilityLambda -> System.out.println("Actual ability: " + abilityLambda.toString()));
        expected.getAbilities().forEach(abilityLambda -> System.out.println("Expected ability: " + abilityLambda.toString()));

        Object[] expectedAbility = expected.getAbilities().toArray();
        Object[] actualAbility = actual.getAbilities().toArray();
        System.out.println("Equals: " + expectedAbility[0].equals(actualAbility[0]));

        Assert.assertEquals(actual.getAbilities(), expected.getAbilities());*/
    }

//    @Mock
//    private MonsterDao monsterDao;
//
//    private MonsterService monsterService;
//
//    @Captor
//    private ArgumentCaptor<Monster> argumentCaptor;
//
//    private Monster lochness;
//    private Monster jack;
//
//    private long createdEntityId = 777l;
//    private long updatedEntityId = 778l;
//    private long existingEntityId = 779l;
//    private long notExistingEntityId = 780l;
//    private String existingName = "I am existing";
//
//    @BeforeClass
//    public void setupMockito() {
//        MockitoAnnotations.initMocks(this);
//
//        //need to enable Aspect on mocked object monsterService
//        ServiceExceptionTranslateAspect translateAspect = new ServiceExceptionTranslateAspect();
//        AspectJProxyFactory factory = new AspectJProxyFactory(new MonsterServiceImpl());
//        factory.addAspect(translateAspect);
//
//        monsterService = factory.getProxy();
//        ReflectionTestUtils.setField(monsterService, "monsterDao", monsterDao);
//    }
//
//    @BeforeMethod
//    public void initMonsters() {
//        lochness = new Monster();
//        lochness.setName("not persisted monster");
//        lochness.setDescription("very massive monster");
//        lochness.setHauntedIntervalStart(LocalTime.of(10, 30));
//        lochness.setHauntedIntervalEnd(LocalTime.of(12, 30));
//
//        jack = new Monster();
//        jack.setName("jack");
//        jack.setDescription("jack description");
//        jack.setHauntedIntervalStart(LocalTime.of(11, 30));
//        jack.setHauntedIntervalEnd(LocalTime.of(13, 30));
//        jack.setId(100l);
//    }
//
//    @BeforeMethod(dependsOnMethods = "initMonsters")
//    public void initMocksBehaviour() {
//        //findByName
//        when(monsterDao.getByName("not persisted monster")).thenReturn(lochness);
//        when(monsterDao.getByName("non existing")).thenReturn(null);
//
//        // getById
//        when(monsterDao.getById(0l)).thenReturn(null);
//        when(monsterDao.getById(1l)).thenReturn(jack);
////        when(sportActivityDao.getById(2l)).thenReturn(footballPersisted);
//
//        doAnswer((InvocationOnMock invocation) -> {
//            throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//        }).when(monsterDao).getById(null);
//
//        //create
//        doAnswer((InvocationOnMock invocation) -> {
//            if (invocation.getArguments()[0] == null) {
//                throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//            }
//
//            Monster monster = (Monster) invocation.getArguments()[0];
//
//            if (monster.getId() != null && monster.getId().equals(existingEntityId)) {
//                throw new EntityExistsException("This is behaviour of EntityManager");
//            }
//
//            if (monster.getName() == null || monster.getName().equals(existingName)) {
//                throw new ConstraintViolationException("This is behaviour of validation on entities", null);
//            }
//            monster.setId(createdEntityId);
//            return null; //this is happy day scenario
//        }).when(monsterDao).create(any(Monster.class));
//
//        //update
//        doAnswer((InvocationOnMock invocation) -> {
//            if (invocation.getArguments()[0] == null) {
//                throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//            }
//
//            Monster monster = (Monster) invocation.getArguments()[0];
//
//            if (monster.getName() == null || monster.getName().equals(existingName)) {
//                throw new ConstraintViolationException("This is behaviour of validation on entities", null);
//            }
//            if (monster.getId() == null) {
//                monster.setId(updatedEntityId);//safe
//            }
//
//            return monster; //this is happy day scenario
//        }).when(monsterDao).update(any(Monster.class));
//
//        //remove
//        doAnswer((InvocationOnMock invocation) -> {
//            Object argument = invocation.getArguments()[0];
//            if (invocation.getArguments()[0] == null) {
//                throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//            }
//
//            Monster monster = (Monster) invocation.getArguments()[0];
//
//            if (monster.getId() == existingEntityId) //happy day scenario
//                return null;
//
//            if (monster.getId() == notExistingEntityId) //entity is not saved
//                throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//
//            return null;
//        }).when(monsterDao).delete(any(Monster.class));
//    }
//
//    @Test
//    public void create() {
//        monsterService.create(lochness);
//        verify(monsterDao).create(argumentCaptor.capture());
//        assertDeepEquals(argumentCaptor.getValue(), lochness);
//        assertEquals((long) lochness.getId(), createdEntityId);
//    }
//
//    private void assertDeepEquals(Monster m1, Monster m2) {
//        assertEquals(m1.getName(), m2.getName());
//        assertEquals(m1.getDescription(), m2.getDescription());
//        assertEquals(m1.getHauntedIntervalStart(), m2.getHauntedIntervalStart());
//        assertEquals(m1.getHauntedIntervalEnd(), m2.getHauntedIntervalEnd());
//    }
}
