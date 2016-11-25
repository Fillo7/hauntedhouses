package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalTime;
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

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final MonsterFacade monsterFacade = new MonsterFacadeImpl();

    @Captor
    ArgumentCaptor<Monster> argumentCaptor;

    private Monster lochness;

    @BeforeClass
    public void setupMockito(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initMonsters(){
        lochness = new Monster();
        lochness.setId(1L);
        lochness.setName("Lochness Monster");
        lochness.setDescription("very massive monster");
        lochness.setHauntedIntervalStart(LocalTime.of(10, 30));
        lochness.setHauntedIntervalEnd(LocalTime.of(12, 30));

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

        monsterFacade.createMonster(jack);
        verify(monsterService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "Jack");
        assertEquals(argumentCaptor.getValue().getDescription(), "very aggressive monster");
        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), LocalTime.of(10, 20));
        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), LocalTime.of(17, 55));
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

        monsterFacade.updateMonster(jack);
        verify(monsterService).update(argumentCaptor.capture());
        assertEquals((long) argumentCaptor.getValue().getId(), 1l);
        assertEquals(argumentCaptor.getValue().getName(), "Jack Sparrow");
        assertEquals(argumentCaptor.getValue().getDescription(), "sweet little kitty");
        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), LocalTime.of(12, 20));
        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), LocalTime.of(13, 40));
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
        assertEquals(monster.getDescription(), monster.getDescription());
        assertEquals(monster.getHauntedIntervalStart(), monster.getHauntedIntervalStart());
        assertEquals(monster.getHauntedIntervalEnd(), monster.getHauntedIntervalEnd());
    }
}
