package cz.fi.muni.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.controller.MonsterRestController;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.MonsterFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Ondrej Oravcok on 09-Dec-16.
 */
@WebAppConfiguration
@ContextConfiguration(classes = RestContextConfiguration.class)
public class MonsterRestControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MonsterFacade monsterFacade;

    @Mock
    private HouseFacade houseFacade;

    @Mock
    private AbilityFacade abilityFacade;

    @Inject
    @InjectMocks
    private MonsterRestController monsterRestController;

    private MockMvc mvcMocker;

    private MonsterDTO kitty;
    private MonsterDTO huggy;

    private HouseDTO house;
    private AbilityDTO ability;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvcMocker = standaloneSetup(monsterRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @BeforeMethod
    public void initMonsters(){
        house = new HouseDTO();
        house.setId(1l);
        house.setName("White House");
        house.setAddress("White Trump's House");

        when(houseFacade.getHouseById(1l)).thenReturn(house);

        ability = new AbilityDTO();
        ability.setId(1l);
        ability.setName("great ability");
        ability.setDescription("really massive big ability");

        when(abilityFacade.getAbilityById(1l)).thenReturn(ability);

        kitty = new MonsterDTO();
        kitty.setId(1l);
        kitty.setName("Little Kitty");
        kitty.setDescription("little sweet kitty");
        kitty.setHauntedIntervalStart(LocalTime.of(10, 20));
        kitty.setHauntedIntervalEnd(LocalTime.of(13, 40));
        kitty.setHouse(houseFacade.getHouseById(1l));
        kitty.addAbility(abilityFacade.getAbilityById(1l));

        huggy = new MonsterDTO();
        huggy.setId(2l);
        huggy.setName("Hyggy Bear");
        huggy.setDescription("Snoop Dogg Huggy Bear");
        huggy.setHauntedIntervalStart(LocalTime.of(13, 20));
        huggy.setHauntedIntervalEnd(LocalTime.of(18, 40));
        huggy.setHouse(houseFacade.getHouseById(1l));
    }

    @BeforeMethod(dependsOnMethods = "initMonsters")
    public void initMocksBehaviour() {
        when(monsterFacade.getMonsterById(kitty.getId())).thenReturn(kitty);
        when(monsterFacade.getMonsterById(huggy.getId())).thenReturn(huggy);

        when(monsterFacade.getAllMonsters()).thenReturn(Arrays.asList(kitty, huggy));
    }

    @Test
    public void getMonstersTest() throws Exception {
        mvcMocker.perform(get("/monsters"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value(kitty.getName()))
                .andExpect(jsonPath("$.[?(@.id==1)].description").value(kitty.getDescription()))
//                .andExpect(jsonPath("$.[?(@.id==1)].hauntedIntervalStart").value(kitty.getHauntedIntervalStart()))
//                .andExpect(jsonPath("$.[?(@.id==1)].hauntedIntervalEnd").value(kitty.getHauntedIntervalEnd()))
//                .andExpect(jsonPath("$.[?(@.id==1)].house").value(kitty.getHouse()))
//                .andExpect(jsonPath("$.[?(@.id==1)].abilities").value(kitty.getAbilities()))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value(huggy.getName()))
                .andExpect(jsonPath("$.[?(@.id==2)].description").value(huggy.getDescription()))
//                .andExpect(jsonPath("$.[?(@.id==2)].hauntedIntervalStart").value(huggy.getHauntedIntervalStart()))
//                .andExpect(jsonPath("$.[?(@.id==2)].hauntedIntervalEnd").value(huggy.getHauntedIntervalEnd()))
//                .andExpect(jsonPath("$.[?(@.id==2)].house").value(huggy.getHouse()))
//                .andExpect(jsonPath("$.[?(@.id==2)].abilities").value(huggy.getAbilities()))
        ;
    }

    @Test
    public void getInvalidMonsterTest() throws Exception {
        when(monsterFacade.getMonsterById(3l)).thenThrow(new NoEntityException("I dont exist"));

        mvcMocker.perform(get("/monsters/3")).andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteMonsterTest() throws Exception {
        doNothing().when(monsterFacade).deleteMonster(1l);

        mvcMocker.perform(delete("/monsters/1")).andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistingMonsterTest() throws Exception {
        doThrow(new NoEntityException("I dont exist")).when(monsterFacade).deleteMonster(3l);

        mvcMocker.perform(delete("/monsters/3")).andExpect(status().isNotFound());
    }
}
