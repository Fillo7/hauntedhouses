package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import java.util.List;
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
public class AbilityFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    @Inject
    AbilityFacade abilityFacade;

    private AbilityCreateDTO createFirst;
    private AbilityCreateDTO createSecond;
    private AbilityDTO ability;

    @BeforeMethod
    public void prepareAbilities() {
        createFirst = new AbilityCreateDTO();
        createFirst.setName("Shameless copy");
        createFirst.setDescription("Shamelessly copies what its target is doing.");

        createSecond = new AbilityCreateDTO();
        createSecond.setName("Another shameless copy");
        createSecond.setDescription("Running out of ideas.");

        ability = new AbilityDTO();
        ability.setName("Charm");
        ability.setDescription("Charms an enemy with its extreme beauty making them do its bidding.");
    }

    @Test
    public void testCreate() {
        Long id = abilityFacade.createAbility(createFirst);
        assertDeepEquals(createFirst, abilityFacade.getAbilityById(id));
    }

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
        Long id = abilityFacade.createAbility(createSecond);
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
