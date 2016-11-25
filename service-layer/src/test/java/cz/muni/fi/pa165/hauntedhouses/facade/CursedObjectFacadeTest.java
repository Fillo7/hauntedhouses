/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.entity.CursedObject;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.service.CursedObjectService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.service.spi.ServiceException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.dao.DataAccessException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Marek Janco
 */

@ContextConfiguration(classes=ServiceConfiguration.class)
public class CursedObjectFacadeTest extends AbstractTransactionalTestNGSpringContextTests{
    
    @Mock
    private CursedObjectService cursedObjectService;
    
    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();
    
    @Mock
    private HouseService houseService;
    
    @InjectMocks
    private final CursedObjectFacade cursedObjectFacade = new CursedObjectFacadeImpl();

    @Captor
    private ArgumentCaptor<CursedObject> captor;
    
    private CursedObject cursedObject;
    private CursedObjectCreateDTO firstCreate;
    private CursedObjectDTO cursedObjectDto;
    private House house;
    private HouseDTO houseDto;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        // house findById
        when(houseService.getById(1L)).thenReturn(house);
        when(houseService.getById(0L)).thenReturn(null);
        
        //find all
        when(cursedObjectService.getAll()).thenReturn(Arrays.asList(cursedObject));
        
        //find by id
        when(cursedObjectService.getById(1L)).thenReturn(cursedObject);
        when(cursedObjectService.getById(0L)).thenReturn(null);
    }
    
    @BeforeMethod
    public void initEntities() {
        house = new House();
        house.setName("big house");
        house.setAddress("somewhere in the world");
        house.setId(1L);
       
        houseDto = new HouseDTO();
        houseDto.setName(house.getName());
        houseDto.setAddress(house.getAddress());
        houseDto.setId(house.getId());
       
        cursedObject = new CursedObject();
        cursedObject.setId(1L);
        cursedObject.setName("cursed axe");
        cursedObject.setDescription("...");
        cursedObject.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        cursedObject.setHouse(house);
        
        firstCreate = new CursedObjectCreateDTO();
        firstCreate.setName(cursedObject.getName());
        firstCreate.setDescription(cursedObject.getDescription());
        firstCreate.setMonsterAttractionFactor(cursedObject.getMonsterAttractionFactor());
        firstCreate.setHouseId(house.getId());
        
        cursedObjectDto = new CursedObjectDTO();
        cursedObjectDto.setId(1L);
        cursedObjectDto.setName(cursedObject.getName());
        cursedObjectDto.setDescription(cursedObject.getDescription());
        cursedObjectDto.setMonsterAttractionFactor(cursedObject.getMonsterAttractionFactor());
        cursedObjectDto.setHouse(houseDto);
    }
    
    @Test
    public void testCreate(){
        cursedObjectFacade.createCursedObject(firstCreate);
        verify(cursedObjectService).create(captor.capture());
        
        assertEquals(captor.getValue().getName(), firstCreate.getName());
        assertEquals(captor.getValue().getDescription(), firstCreate.getDescription());
        assertEquals(captor.getValue().getMonsterAttractionFactor(), firstCreate.getMonsterAttractionFactor());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createNullCursedObjectTest(){
        cursedObjectFacade.createCursedObject(null);
    }
    
    @Test
    public void testUpdate(){
        cursedObjectFacade.updateCursedObject(cursedObjectDto);
        verify(cursedObjectService).update(captor.capture());
        
        assertEquals(captor.getValue().getName(), cursedObjectDto.getName());
        assertEquals(captor.getValue().getDescription(), cursedObjectDto.getDescription());
        assertEquals(captor.getValue().getMonsterAttractionFactor(), cursedObjectDto.getMonsterAttractionFactor());
        assertEquals(captor.getValue().getHouse().getId(), cursedObjectDto.getHouse().getId());
        assertDeepEquals(captor.getValue().getHouse(), cursedObjectDto.getHouse());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullTest(){
        cursedObjectFacade.updateCursedObject(null);
    }
    
    @Test
    public void testFindById(){
        CursedObjectDTO found = cursedObjectFacade.getCursedObjectWithId(1L);
        assertDeepEquals(firstCreate, found);
    }
    
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getWithNullIdTest(){
        cursedObjectFacade.getCursedObjectWithId(null);
    }
     
    @Test
    public void getNoExistingCursedObjectTest(){
        assertNull(cursedObjectFacade.getCursedObjectWithId(0L));
    }
   
    @Test
    public void testFindAll(){
        
        List<CursedObjectDTO> list = new ArrayList<>();
        list.add(cursedObjectDto);
        
        cursedObjectFacade.createCursedObject(firstCreate);
        
        List<CursedObjectDTO> founded = cursedObjectFacade.getAllCursedObjects();
        assertDeepEquals(firstCreate, founded.get(0));
        assertEquals(list, founded);
    }
    
    @Test
    public void testDelete(){
        cursedObjectFacade.deleteCursedObject(cursedObjectDto.getId());
        verify(cursedObjectService).delete(captor.capture());
        
        assertEquals(cursedObjectDto.getId(), captor.getValue().getId());
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullTest(){
        cursedObjectFacade.deleteCursedObject(null);
    }

    private void assertDeepEquals(CursedObjectCreateDTO first, CursedObjectDTO second) {
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getDescription(), second.getDescription());
        assertEquals(first.getMonsterAttractionFactor(), second.getMonsterAttractionFactor());
        assertEquals(first.getHouseId(), second.getHouse().getId());
    }
    
    private void assertDeepEquals(House first, HouseDTO second) {
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getAddress(), second.getAddress());
    }
    
   
}
