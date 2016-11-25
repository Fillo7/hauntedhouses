/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.BeanMappingService;
import cz.muni.fi.pa165.hauntedhouses.BeanMappingServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.entity.House;
import cz.muni.fi.pa165.hauntedhouses.exception.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Basic CRUD tests of the House facade layer.
 * @author Kristyna Loukotova
 * @version 25.11.2016
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class HouseFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseService houseService;

    @InjectMocks
    HouseFacade houseFacade = new HouseFacadeImpl();

    @Spy
    @Inject
    private final BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    @Captor
    ArgumentCaptor<House> argumentCaptor;

    private House house;

    @BeforeClass
    public void setupMockito(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareHouse() {
        house = new House();
        house.setId(1L);
        house.setName("Yolo");
        house.setAddress("Rolo");

        when(houseService.getById(0L)).thenReturn(null);
        when(houseService.getById(1L)).thenReturn(house);
        when(houseService.getByName(null)).thenReturn(null);
        when(houseService.getByName("Yolo")).thenReturn(house);
    }

    @Test
    public void testCreate() {
        HouseCreateDTO houseCreate = new HouseCreateDTO();
        houseCreate.setName("Jogobella");
        houseCreate.setAddress("When I was young I wanted to be the Jogobella fruit");

        houseFacade.createHouse(houseCreate);
        verify(houseService).create(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getName(), "Jogobella");
        assertEquals(argumentCaptor.getValue().getAddress(), "When I was young I wanted to be the Jogobella fruit");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull(){
        houseFacade.createHouse(null);
    }

    @Test
    public void testUpdate() {
        HouseDTO houseDto = new HouseDTO();
        houseDto.setId(1L);
        houseDto.setName("Housy house");
        houseDto.setAddress("Housity housy house");

        houseFacade.updateHouse(houseDto);
        verify(houseService).update(argumentCaptor.capture());
        assertEquals((long) argumentCaptor.getValue().getId(), 1L);
        assertEquals(argumentCaptor.getValue().getName(), "Housy house");
        assertEquals(argumentCaptor.getValue().getAddress(), "Housity housy house");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        houseFacade.updateHouse(null);
    }

    @Test
    public void testDelete() {
        houseFacade.deleteHouse(1L);
        verify(houseService).delete(argumentCaptor.capture());
        if (argumentCaptor == null) {
            System.out.println("Argument captor is null");
        }
        if (argumentCaptor.getValue() == null) {
            System.out.println("Argument captor VALUE is null");
        }
        if (argumentCaptor.getValue().getId() == null) {
            System.out.println("Argument captor value ID is null");
        }
        assertEquals((long) argumentCaptor.getValue().getId(), 1L);
        assertEquals(argumentCaptor.getValue().getName(), house.getName());
        assertEquals(argumentCaptor.getValue().getAddress(), house.getAddress());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        houseFacade.deleteHouse(null);
    }

    @Test(expectedExceptions = NoEntityException.class)
    public void testDeleteNonexistent() {
        houseFacade.deleteHouse(0L);
    }

    @Test
    public void testGetById() {
        HouseDTO houseDto = houseFacade.getHouseById(1L);
        assertEquals(houseDto.getId(), house.getId());
        assertEquals(houseDto.getName(), house.getName());
        assertEquals(houseDto.getAddress(), house.getAddress());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetByIdNull() {
        houseFacade.getHouseById(null);
    }

    @Test(expectedExceptions = NoEntityException.class)
    public void testGetByIdNonexistent() {
        houseFacade.getHouseById(0L);
    }

    @Test
    public void testGetByName() {
        HouseDTO houseDto = houseFacade.getHouseByName("Yolo");
        assertEquals(houseDto.getId(), house.getId());
        assertEquals(houseDto.getName(), house.getName());
        assertEquals(houseDto.getAddress(), house.getAddress());
    }

    @Test
    public void testGetAll() {
        List<House> houses = new ArrayList<>();
        houses.add(house);

        when(houseService.getAll()).thenReturn(houses);
        List<HouseDTO> foundHouses = houseFacade.getAllHouses();

        assertEquals(foundHouses.size(), 1);
        assertEquals(foundHouses.get(0).getId(), house.getId());
        assertEquals(foundHouses.get(0).getName(), house.getName());
        assertEquals(foundHouses.get(0).getAddress(), house.getAddress());
    }
}
