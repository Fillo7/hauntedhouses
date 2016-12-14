/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.controller.CursedObjectRestController;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.facade.CursedObjectFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Arrays;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 *
 * @author Marek Janco
 */
@WebAppConfiguration
@ContextConfiguration(classes = RestContextConfiguration.class)
public class CursedObjectRestControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CursedObjectFacade cursedObjectFacade;

    @Mock
    private HouseFacade houseFacade;

    @Inject
    @InjectMocks
    private CursedObjectRestController cursedObjectRestController;

    private MockMvc mockMvc;

    private CursedObjectDTO doll;
    private CursedObjectDTO mirror;
    private HouseDTO house;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(cursedObjectRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @BeforeMethod
    public void initEntities() {
        house = new HouseDTO();
        house.setId(1l);
        house.setName("scary house full of crazy people");
        house.setAddress("Brno, FI, botanicka");

        doll = new CursedObjectDTO();
        doll.setId(1l);
        doll.setName("cursed creepy doll");
        doll.setDescription("doll scares all people around her");
        doll.setMonsterAttractionFactor(MonsterAttractionFactor.MEDIUM);
        doll.setHouseId(house.getId());

        mirror = new CursedObjectDTO();
        mirror.setId(2l);
        mirror.setName("Mirror of death");
        mirror.setDescription("causes hallucinations");
        mirror.setMonsterAttractionFactor(MonsterAttractionFactor.INSANE);
        mirror.setHouseId(house.getId());
    }

    @BeforeMethod(dependsOnMethods = "initEntities")
    public void initMocksBehaviour() {
        //finds all
        when(cursedObjectFacade.getAllCursedObjects()).thenReturn(Arrays.asList(doll, mirror));

        //find by id
        when(cursedObjectFacade.getCursedObjectWithId(doll.getId())).thenReturn(doll);
        when(cursedObjectFacade.getCursedObjectWithId(mirror.getId())).thenReturn(mirror);
    }

    @Test
    public void getCursedObjectWithIdTest() throws Exception {

        mockMvc.perform(get(Uri.CURSED_OBJECTS + "/" + doll.getId())).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].name").value(doll.getName()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].description").value(doll.getDescription()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].monsterAttractionFactor").value(doll.getMonsterAttractionFactor().toString()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].houseId").value(doll.getHouseId().intValue()));

        mockMvc.perform(get(Uri.CURSED_OBJECTS + "/" + mirror.getId() + "")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].name").value(mirror.getName()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].description").value(mirror.getDescription()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].monsterAttractionFactor").value(mirror.getMonsterAttractionFactor().toString()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].houseId").value(mirror.getHouseId().intValue()));
    }

    @Test
    public void getAllCursedObjectsTest() throws Exception {

        mockMvc.perform(get(Uri.CURSED_OBJECTS)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].name").value(doll.getName()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].description").value(doll.getDescription()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].monsterAttractionFactor").value(doll.getMonsterAttractionFactor().toString()))
                .andExpect(jsonPath("$.[?(@.id==" + doll.getId() + ")].houseId").value(doll.getHouseId().intValue()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].name").value(mirror.getName()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].description").value(mirror.getDescription()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].monsterAttractionFactor").value(mirror.getMonsterAttractionFactor().toString()))
                .andExpect(jsonPath("$.[?(@.id==" + mirror.getId() + ")].houseId").value(mirror.getHouseId().intValue()));
    }
    
    @Test
    public void deleteCursedObjectTest() {
        
    }
}
