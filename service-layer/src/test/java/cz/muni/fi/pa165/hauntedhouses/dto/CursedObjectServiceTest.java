package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author Filip Petrovic (422334)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CursedObjectServiceTest extends AbstractTestNGSpringContextTests {
    @Inject
    @InjectMocks
    private CursedObjectService cursedObjectService;
}
