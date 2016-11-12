package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.CursedObjectDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
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
public class CursedObjectServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private CursedObjectDao cursedObjectDao;
    
    @Inject
    @InjectMocks
    private CursedObjectService cursedObjectService;
    
    private CursedObject one;
    private CursedObject two;
    
    @BeforeMethod
    public void setCursedObjects() {
        one = new CursedObject();
        one.setName("Ghostly carrot");
        one.setDescription("Attracts all types of horrifying ghostly rabbits.");
        one.setMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);
        
        two = new CursedObject();
        two.setName("Ancient grog bottle.");
        two.setDescription("YARRRR!");
        two.setMonsterAttractionFactor(MonsterAttractionFactor.HIGH);
    }
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
}
