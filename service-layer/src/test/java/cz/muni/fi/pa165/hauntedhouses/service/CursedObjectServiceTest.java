package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dao.CursedObjectDao;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import java.util.Arrays;
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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Marek Janco
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CursedObjectServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CursedObjectDao cursedObjectDao;

    @Inject
    @InjectMocks
    private CursedObjectService cursedObjectService;

    private CursedObject cursedObject;
    private CursedObject cursedObject2;
    private House house;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initEntities() {
        house = new House();
        house.setName("the House");
        house.setAddress("somewhere");
        house.setId(1l);

        cursedObject = new CursedObject();
        cursedObject.setId(1l);
        cursedObject.setName("new object");
        cursedObject.setDescription("object is somehow cursed");
        cursedObject.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        cursedObject.setHouse(house);

        cursedObject2 = new CursedObject();
        cursedObject2.setId(2l);
        cursedObject2.setName("new object");
        cursedObject2.setDescription("object is somehow cursed");
        cursedObject2.setMonsterAttractionFactor(MonsterAttractionFactor.LOW);
        cursedObject2.setHouse(house);

    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        //update
        when(cursedObjectDao.update(cursedObject)).thenReturn(cursedObject);

        //get by id
        when(cursedObjectDao.getById(1l)).thenReturn(cursedObject);
        when(cursedObjectDao.getById(0l)).thenReturn(null);

        //get all
        when(cursedObjectDao.getAll()).thenReturn(Arrays.asList(cursedObject));

    }

    @Test
    public void createCursedObjectTest() {
        cursedObjectService.create(cursedObject);
        verify(cursedObjectDao, times(1)).create(cursedObject);
    }

    @Test
    public void updateCursedObjectTest() {
        CursedObject updated = cursedObjectService.update(cursedObject);
        assertDeepEquals(updated, cursedObject);
    }

    @Test
    public void getCursedObjectByIdTest() {
        Assert.assertNotNull(cursedObject.getId());

        CursedObject found = cursedObjectService.getById(1l);
        Assert.assertNotNull(cursedObject);
        Assert.assertNotNull(found);

        assertDeepEquals(found, cursedObject);
    }

    @Test
    public void deleteCursedObjectTest() {
        cursedObjectService.delete(cursedObject);
        verify(cursedObjectDao).delete(cursedObject);
    }

    @Test
    public void getAllCursedObjectTest() {
        List<CursedObject> founded = cursedObjectService.getAll();

        Assert.assertNotNull(founded);
        Assert.assertTrue(founded.contains(cursedObject));
        Assert.assertEquals(founded, Arrays.asList(cursedObject));
    }

    @Test
    public void massIncreaseMonsterAttractionFactorTest() {
        cursedObject.setMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);
        cursedObject2.setMonsterAttractionFactor(MonsterAttractionFactor.LOW);
        when(cursedObjectDao.getAll()).thenReturn(Arrays.asList(cursedObject, cursedObject2));
        when(cursedObjectDao.update(cursedObject)).thenReturn(cursedObject);
        when(cursedObjectDao.update(cursedObject2)).thenReturn(cursedObject2);

        cursedObjectService.massIncreaseMonsterAttractionFactor(MonsterAttractionFactor.HIGH);

        Assert.assertEquals(cursedObject.getMonsterAttractionFactor(), MonsterAttractionFactor.HIGH);
        Assert.assertEquals(cursedObject2.getMonsterAttractionFactor(), MonsterAttractionFactor.MEDIUM);

        cursedObjectService.massIncreaseMonsterAttractionFactor(MonsterAttractionFactor.LOW);

        Assert.assertEquals(cursedObject.getMonsterAttractionFactor(), MonsterAttractionFactor.HIGH);
        Assert.assertEquals(cursedObject2.getMonsterAttractionFactor(), MonsterAttractionFactor.MEDIUM);

        cursedObjectService.massIncreaseMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);

        Assert.assertEquals(cursedObject.getMonsterAttractionFactor(), MonsterAttractionFactor.HIGH);
        Assert.assertEquals(cursedObject2.getMonsterAttractionFactor(), MonsterAttractionFactor.MEDIUM);
    }
    
    /**
     * Method fails if cursed objects differs and success if they equals
     * 
     * @param updated
     * @param cursedObject 
     */
    private void assertDeepEquals(CursedObject updated, CursedObject cursedObject) {
        Assert.assertEquals(updated, cursedObject);
        Assert.assertEquals(updated.getName(), cursedObject.getName());
        Assert.assertEquals(updated.getDescription(), cursedObject.getDescription());
        Assert.assertEquals(updated.getMonsterAttractionFactor(), cursedObject.getMonsterAttractionFactor());
        Assert.assertEquals(updated.getHouse(), cursedObject.getHouse());
    }
}
