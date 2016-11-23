package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by User on 23-Nov-16.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HouseFacadeExampleTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseService houseService;

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @InjectMocks
    private final HouseFacade houseFacade = new HouseFacadeImpl();

    @Captor
    ArgumentCaptor<House> argumentCaptor;

    private House lochness;

    @BeforeClass
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void initHouses() {
        lochness = new House();
        lochness.setName("Lochness house");
        lochness.setId(1l);
        lochness.setAddress("doma");

        when(houseService.findById(0l)).thenReturn(null);
        when(houseService.findById(1l)).thenReturn(lochness);

        when(houseService.findByName("Lochness house")).thenReturn(lochness);
        when(houseService.findByName("non existing")).thenReturn(null);
    }

    @Test
    public void createHouse() {
        HouseCreateDTO dto = new HouseCreateDTO();
        dto.setName("New House");
        dto.setAddress("New House Address");

        houseFacade.createHouse(dto);
        verify(houseService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "New House");
        assertEquals(argumentCaptor.getValue().getAddress(), "New House Address");
    }
}
