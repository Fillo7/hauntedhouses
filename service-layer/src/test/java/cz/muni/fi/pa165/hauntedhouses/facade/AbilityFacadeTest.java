package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
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
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AbilityFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    @Mock
    AbilityService abilityService;
    
    @Mock
    BeanMappingService beanMappingService;
    
    @Inject
    @InjectMocks
    AbilityFacade abilityFacade;

    private AbilityCreateDTO createFirst;
    private AbilityCreateDTO createSecond;
    private AbilityDTO ability;
    
    private Ability first;
    private Ability second;
    
    @BeforeClass
    public void setUpClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void prepareAbilities() {
        createFirst = new AbilityCreateDTO();
        createFirst.setName("Shameless copy");
        createFirst.setDescription("Shamelessly copies what its target is doing.");
        
        first = new Ability();
        first.setId(0L);
        first.setName("Shameless copy");
        first.setDescription("Shamelessly copies what its target is doing.");

        createSecond = new AbilityCreateDTO();
        createSecond.setName("Another shameless copy");
        createSecond.setDescription("Running out of ideas.");
        
        second = new Ability();
        second.setId(1L);
        second.setName("Another shameless copy");
        second.setDescription("Running out of ideas.");

        ability = new AbilityDTO();
        ability.setName("Charm");
        ability.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
        
        when(beanMappingService.mapTo(createFirst, Ability.class)).thenReturn(first);
    }

    /*@Test
    public void testCreate() {
        abilityFacade.createAbility(createFirst);
        verify(abilityService, times(1)).create(first);
    }*/

    @Test
    public void testUpdate() {
        Long id = abilityFacade.createAbility(createFirst);
        ability.setId(id);
        abilityFacade.updateAbility(ability);

        assertDeepEquals(ability, abilityFacade.getAbilityById(id));
    }

    @Test
    public void testDelete() {
        Long id = abilityFacade.createAbility(createFirst);
        Assert.assertEquals(abilityFacade.getAllAbilities().size(), 1);

        abilityFacade.deleteAbility(id);
        Assert.assertEquals(abilityFacade.getAllAbilities().size(), 0);
    }

    @Test
    public void testGetById() {
        abilityFacade.createAbility(createFirst);
        Long id = abilityFacade.createAbility(createSecond);

        AbilityDTO returned = abilityFacade.getAbilityById(id);
        assertDeepEquals(createSecond, returned);
    }

    @Test
    public void testGetByName() {
        abilityFacade.createAbility(createFirst);
        abilityFacade.createAbility(createSecond);
        String name = createSecond.getName();

        AbilityDTO returned = abilityFacade.getAbilityByName(name);
        assertDeepEquals(createSecond, returned);
    }

    @Test
    public void testGetAll() {
        abilityFacade.createAbility(createFirst);
        abilityFacade.createAbility(createSecond);

        List<AbilityDTO> returned = abilityFacade.getAllAbilities();
        Assert.assertEquals(returned.size(), 2);

        assertDeepEquals(createFirst, returned.get(0));
        assertDeepEquals(createSecond, returned.get(1));
    }

    private void assertDeepEquals(AbilityCreateDTO actual, AbilityDTO expected) {
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
    }

    private void assertDeepEquals(AbilityDTO actual, AbilityDTO expected) {
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
    }
}
