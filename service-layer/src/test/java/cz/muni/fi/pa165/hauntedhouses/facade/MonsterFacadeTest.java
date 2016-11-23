package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.MonsterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import org.dozer.inject.Inject;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalTime;

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

//    @Mock
//    private MonsterService monsterService;
//
//    @Spy
//    @Inject
//    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();
//
//    @InjectMocks
//    private final MonsterFacade monsterFacade = new MonsterFacadeImpl();
//
//    @Captor
//    ArgumentCaptor<Monster> argumentCaptor;
//
//    private Monster lochness;
//
//    @BeforeClass
//    public void setupMockito(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @BeforeMethod
//    public void initMonsters(){
//        lochness = new Monster();
//        lochness.setId(1L);
//
//        when(monsterService.findById(0L)).thenReturn(null);
//        when(monsterService.findById(1L)).thenReturn(lochness);
//    }
//
//    @Test
//    public void createMonsterTest(){
//        MonsterCreateDTO jack = new MonsterCreateDTO();
//        jack.setName("Jack");
//        jack.setDescription("very aggressive monster");
//        jack.setHauntedIntervalStart(LocalTime.of(10, 20));
//        jack.setHauntedIntervalEnd(LocalTime.of(17, 55));
//
//        monsterFacade.createMonster(jack);
//        verify(monsterService).create(argumentCaptor.capture());
//        assertEquals(argumentCaptor.getValue().getName(), "Jack");
//        assertEquals(argumentCaptor.getValue().getDescription(), "very aggressive monster");
//        assertEquals(argumentCaptor.getValue().getHauntedIntervalStart(), LocalTime.of(10, 20));
//        assertEquals(argumentCaptor.getValue().getHauntedIntervalEnd(), LocalTime.of(17, 55));
//    }
//
//    @Test(expectedExceptions = IllegalArgumentException.class)
//    public void createNullMonsterTest(){
//        monsterFacade.createMonster(null);
//    }
}
