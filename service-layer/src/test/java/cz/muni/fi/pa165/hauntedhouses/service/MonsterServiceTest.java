/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
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
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Kristyna Loukotova
 * @version 24.11.2016
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
    public void testFindById() {
        Long id = monster.getId();
        Assert.assertNotNull(id);

        when(monsterDao.findById(1L)).thenReturn(monster);
        Monster foundMonster = monsterService.findById(id);

        Assert.assertNotNull(monster);
        Assert.assertNotNull(foundMonster);
        this.assertDeepEquals(foundMonster, monster);
    }

    @Test
    public void testFindAll() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(monster);

        when(monsterDao.findAll()).thenReturn(monsters);
        List<Monster> foundMonsters = monsterService.findAll();

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

        when(monsterDao.findById(1L)).thenReturn(monster);
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

        when(monsterDao.findById(1L)).thenReturn(monster);
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
}
