package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.MonsterDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.exceptions.ServiceExceptionTranslateAspect;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.time.LocalTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test class for MonsterService
 *
 * Created by Ondrej Oravcok on 23-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MonsterServiceTest extends AbstractTestNGSpringContextTests {

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
//        when(monsterDao.findByName("not persisted monster")).thenReturn(lochness);
//        when(monsterDao.findByName("non existing")).thenReturn(null);
//
//        // findById
//        when(monsterDao.findById(0l)).thenReturn(null);
//        when(monsterDao.findById(1l)).thenReturn(jack);
////        when(sportActivityDao.findById(2l)).thenReturn(footballPersisted);
//
//        doAnswer((InvocationOnMock invocation) -> {
//            throw new InvalidDataAccessApiUsageException("This behaviour is already tested on dao layer.");
//        }).when(monsterDao).findById(null);
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
