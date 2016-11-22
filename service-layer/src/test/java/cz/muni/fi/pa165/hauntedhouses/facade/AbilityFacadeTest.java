package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import java.util.HashSet;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
@Transactional
public class AbilityFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    @Inject
    AbilityFacade abilityFacade;
    
    private AbilityCreateDTO create;
    private AbilityDTO ability;
    
    @BeforeMethod
    public void prepareHouse() {
        create = new AbilityCreateDTO();
        create.setName("Shameless copy");
        create.setDescription("Shamelessly copies what its target is doing.");

        ability = new AbilityDTO();
        ability.setName("Charm");
        ability.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
    }
    
    @Test
    public void testCreate() {
        Long id = abilityFacade.createAbility(create);
        assertDeepEquals(create, abilityFacade.getAbilityById(id));
    }

    @Test
    public void testUpdate() {
        Long id = abilityFacade.createAbility(create);
        ability.setId(id);
        abilityFacade.updateAbility(ability);
        
        assertDeepEquals(ability, abilityFacade.getAbilityById(id));
    }

    @Test
    public void testRemove() {
        
    }

    @Test
    public void testFindById() {
        
    }

    @Test
    public void testFindAll() {
        
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
