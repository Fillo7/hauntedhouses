package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.entity.Ability;
import cz.muni.fi.pa165.hauntedhouses.entity.Monster;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

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
        monster.setName("Doppelganger");
        monster.setDescription("Its a doppelganger.");
        monster.addAbility(one);
        
        one = new Ability();
        one.setName("Shameless copy");
        one.setDescription("Shamelessly copies what its target is doing.");
        one.addMonster(monster);
        
        two = new Ability();
        two.setName("Charm");
        two.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
    }
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
}
