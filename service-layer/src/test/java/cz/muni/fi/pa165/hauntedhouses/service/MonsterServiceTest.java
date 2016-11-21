package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.api.facade.MonsterFacade;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacadeImpl;
import org.dozer.inject.Inject;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

/**
 * Test class for MonsterService
 *
 * Created by Ondrej Oravcok on 21-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MonsterServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MonsterService monsterService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final MonsterFacade monsterFacade = new MonsterFacadeImpl();

    @Captor
    ArgumentCaptor<Monster> argumentCaptor;

    @BeforeClass
    public void setupMockito(){
        MockitoAnnotations.initMocks(this);
    }
}
