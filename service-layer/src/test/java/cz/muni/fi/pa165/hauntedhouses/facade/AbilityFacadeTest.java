package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.MonsterService;
import java.util.ArrayList;
import java.util.List;
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
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AbilityFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    AbilityService abilityService;

    @Mock
    private MonsterService monsterService;

    @Mock
    BeanMappingService beanMappingService;

    @InjectMocks
    AbilityFacade abilityFacade = new AbilityFacadeImpl();

    private AbilityCreateDTO createFirst;
    private AbilityCreateDTO createSecond;
    private AbilityDTO dtoFirst;
    private AbilityDTO dtoThird;

    private Ability first;
    private Ability second;
    private Ability third;
    private Ability empty;

    private Monster monster;

    @BeforeMethod
    public void prepareAbilities() {
        monster = new Monster();
        monster.setId(1L);
        monster.setName("Scary monster");
        monster.setDescription("Scares even itself");

        when(monsterService.getById(0L)).thenReturn(null);
        when(monsterService.getById(1L)).thenReturn(monster);

        createFirst = new AbilityCreateDTO();
        createFirst.setName("Shameless copy");
        createFirst.setDescription("Shamelessly copies what its target is doing.");
        createFirst.addMonsterId(1L);

        dtoFirst = new AbilityDTO();
        dtoFirst.setName("Shameless copy");
        dtoFirst.setDescription("Shamelessly copies what its target is doing.");
        dtoFirst.addMonsterId(1L);

        first = new Ability();
        first.setName("Shameless copy");
        first.setDescription("Shamelessly copies what its target is doing.");
        first.addMonster(monster);

        createSecond = new AbilityCreateDTO();
        createSecond.setName("Another shameless copy");
        createSecond.setDescription("Running out of ideas.");

        second = new Ability();
        second.setName("Another shameless copy");
        second.setDescription("Running out of ideas.");

        dtoThird = new AbilityDTO();
        dtoThird.setName("Charm");
        dtoThird.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");

        third = new Ability();
        third.setName("Charm");
        third.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");

        empty = new Ability();
        empty.setId(0L);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        when(beanMappingService.mapTo(createFirst, Ability.class)).thenReturn(first);

        abilityFacade.createAbility(createFirst);
        verify(abilityService, times(1)).create(first);
    }

    @Test
    public void testUpdate() {
        when(beanMappingService.mapTo(dtoThird, Ability.class)).thenReturn(third);

        abilityFacade.updateAbility(dtoThird);
        verify(abilityService, times(1)).update(third);
    }

    @Test
    public void testDelete() {
        abilityFacade.deleteAbility(0L);
        verify(abilityService, times(1)).delete(empty);
    }

    @Test
    public void testGetById() {
        Long id = 0L;
        when(abilityService.getById(id)).thenReturn(third);
        when(beanMappingService.mapTo(third, AbilityDTO.class)).thenReturn(dtoThird);

        AbilityDTO returned = abilityFacade.getAbilityById(id);
        assertDeepEquals(returned, third);
    }

    @Test
    public void testGetByName() {
        when(abilityService.getByName(third.getName())).thenReturn(third);
        when(beanMappingService.mapTo(third, AbilityDTO.class)).thenReturn(dtoThird);

        AbilityDTO returned = abilityFacade.getAbilityByName(third.getName());
        assertDeepEquals(returned, third);
    }

    @Test
    public void testGetAll() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(first);
        abilities.add(third);

        List<AbilityDTO> abilityDTOs = new ArrayList<>();
        abilityDTOs.add(dtoFirst);
        abilityDTOs.add(dtoThird);

        when(abilityService.getAll()).thenReturn(abilities);
        when(beanMappingService.mapTo(abilities, AbilityDTO.class)).thenReturn(abilityDTOs);

        List<AbilityDTO> returned = abilityFacade.getAllAbilities();

        Assert.assertEquals(returned.size(), 2);
        assertDeepEquals(returned.get(0), first);
        assertDeepEquals(returned.get(1), third);
    }

    private void assertDeepEquals(AbilityDTO actual, Ability expected) {
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        //Assert.assertEquals(actual.getMonsterIds(), expected.getMonsters());
    }
}
