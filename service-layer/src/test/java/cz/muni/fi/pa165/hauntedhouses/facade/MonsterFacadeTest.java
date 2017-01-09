package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test class for MonsterService
 *
 * Created by Ondrej Oravcok on 21-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MonsterFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MonsterService monsterService;

    @Mock
    private HouseService houseService;

    @Mock
    private AbilityService abilityService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final MonsterFacade monsterFacade = new MonsterFacadeImpl();

    @Captor
    ArgumentCaptor<Monster> argumentCaptor;

    private House house1;
    private House house2;
    private Ability ability;
    private Monster lochness;

    @BeforeClass
    public void setupMockito(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities(){
        house1 = new House();
        house1.setId(1l);
        house1.setName("House");
        house1.setAddress("At home");

        house2 = new House();
        house2.setId(2l);
        house2.setName("House Bad");
        house2.setAddress("Aborogin house");

        when(houseService.getById(0l)).thenReturn(null);
        when(houseService.getById(1l)).thenReturn(house1);
        when(houseService.getById(2l)).thenReturn(house2);

        ability = new Ability();
        ability.setId(1l);
        ability.setName("Ability Super");
        ability.setDescription("Super Mega Ultra Ability");

        when(abilityService.getById(0l)).thenReturn(null);
        when(abilityService.getById(1l)).thenReturn(ability);

        lochness = new Monster();
        lochness.setId(1L);
        lochness.setName("Lochness Monster");
        lochness.setDescription("very massive monster");
        lochness.setHauntedIntervalStart(LocalTime.of(10, 30));
        lochness.setHauntedIntervalEnd(LocalTime.of(12, 30));
        lochness.setHouse(house1);
        lochness.addAbility(ability);

        when(monsterService.getById(0L)).thenReturn(null);
        when(monsterService.getById(1L)).thenReturn(lochness);
    }

    @Test
    public void createMonsterTest(){
        MonsterCreateDTO jack = new MonsterCreateDTO();
        jack.setName("Jack");
        jack.setDescription("very aggressive monster");
        jack.setHauntedIntervalStart(LocalTime.of(10, 20));
        jack.setHauntedIntervalEnd(LocalTime.of(17, 55));
        jack.setHouseId(1l);
        jack.addAbilityId(1l);

        monsterFacade.createMonster(jack);
        verify(monsterService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "Jack");
        assertEquals(argumentCaptor.getValue().getDescription(), "very aggressive monster");
        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), LocalTime.of(10, 20));
        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), LocalTime.of(17, 55));
        assertEquals(argumentCaptor.getValue().getHouse(), house1);
        assertEquals(argumentCaptor.getValue().getAbilities(), new HashSet<>(Arrays.asList(ability)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullMonsterTest(){
        monsterFacade.createMonster(null);
    }

    @Test
    public void updateMonsterTest() {
        MonsterDTO jack = new MonsterDTO();
        jack.setId(1l);
        jack.setName("Jack Sparrow");
        jack.setDescription("sweet little kitty");
        jack.setHauntedIntervalStart(LocalTime.of(12, 20));
        jack.setHauntedIntervalEnd(LocalTime.of(13, 40));
        jack.setHouseId(2L);

        monsterFacade.updateMonster(jack);
        verify(monsterService).update(argumentCaptor.capture());
        assertEquals((long) argumentCaptor.getValue().getId(), 1l);
        assertEquals(argumentCaptor.getValue().getName(), "Jack Sparrow");
        assertEquals(argumentCaptor.getValue().getDescription(), "sweet little kitty");
        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), LocalTime.of(12, 20));
        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), LocalTime.of(13, 40));
        assertEquals(argumentCaptor.getValue().getHouse(), house2);
        assertEquals(argumentCaptor.getValue().getAbilities(), new HashSet<Ability>());
    }

    @Test(expectedExceptions = NoEntityException.class)
    public void updateNonExistingMonsterTest() {
        MonsterDTO jack = new MonsterDTO();
        jack.setId(0l);
        jack.setName("Jack Sparrow");
        jack.setDescription("sweet little kitty");
        jack.setHauntedIntervalStart(LocalTime.of(12, 20));
        jack.setHauntedIntervalEnd(LocalTime.of(13, 40));

        monsterFacade.updateMonster(jack);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullMonsterTest() {
        monsterFacade.updateMonster(null);
    }

    @Test
    public void deleteMonsterTest() {
        monsterFacade.deleteMonster(1l);
        verify(monsterService).delete(argumentCaptor.capture());
        assertEquals((long) argumentCaptor.getValue().getId(), 1l);
        assertEquals(argumentCaptor.getValue().getName(), lochness.getName());
        assertEquals(argumentCaptor.getValue().getDescription(), lochness.getDescription());
        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), lochness.getHauntedIntervalStart());
        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), lochness.getHauntedIntervalEnd());
    }

    @Test(expectedExceptions = NoEntityException.class)
    public void deleteNonExistingMonster(){
        monsterFacade.deleteMonster(0l);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullMonsterTest() {
        monsterFacade.deleteMonster(null);
    }

    @Test
    public void getMonsterByIdTest() {
        MonsterDTO monster = monsterFacade.getMonsterById(1l);
        assertEquals(monster.getId(), lochness.getId());
        assertEquals(monster.getName(), lochness.getName());
        assertEquals(monster.getDescription(), lochness.getDescription());
        assertEquals(monster.getHauntedIntervalStart(), lochness.getHauntedIntervalStart());
        assertEquals(monster.getHauntedIntervalEnd(), lochness.getHauntedIntervalEnd());
    }
}
