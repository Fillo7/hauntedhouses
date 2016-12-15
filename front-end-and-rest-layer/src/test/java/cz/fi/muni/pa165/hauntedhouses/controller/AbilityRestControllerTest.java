/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.controller.AbilityRestController;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import java.time.LocalTime;
import java.util.Arrays;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Kristyna Loukotova
 * @version 15.12.2016
 */
@WebAppConfiguration
@ContextConfiguration(classes = RestContextConfiguration.class)
public class AbilityRestControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AbilityFacade abilityFacade;

    @Inject
    @InjectMocks
    private AbilityRestController abilityRestController;

    private MockMvc mvcMocker;

    private AbilityDTO ability1;
    private AbilityDTO ability2;
    private MonsterDTO monster;

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvcMocker = standaloneSetup(abilityRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @BeforeMethod
    public void initEntities(){

        monster = new MonsterDTO();
        monster.setId(1L);
        monster.setName("Monster");
        monster.setDescription("Monster description");

        ability1 = new AbilityDTO();
        ability1.setId(1L);
        ability1.setName("Ability 1");
        ability1.setDescription("Ability 1 description");
        ability1.addMonsterId(monster.getId());

        ability2 = new AbilityDTO();
        ability2.setId(2L);
        ability2.setName("Ability 2");
        ability2.setDescription("Ability 2 description");

        when(abilityFacade.getAbilityById(1L)).thenReturn(ability1);
        when(abilityFacade.getAbilityById(2L)).thenReturn(ability2);

        when(abilityFacade.getAllAbilities()).thenReturn(Arrays.asList(ability1, ability2));
    }

    @Test
    public void createAbilityTest() {

    }

    @Test
    public void getAllAbilitiesTest() throws Exception {
        mvcMocker.perform(get("/abilities"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value(ability1.getName()))
                .andExpect(jsonPath("$.[?(@.id==1)].description").value(ability1.getDescription()))
//                .andExpect(jsonPath("$.[?(@.id==1)].monsterIds").value(ability1.getMonsterIds()))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value(ability2.getName()))
                .andExpect(jsonPath("$.[?(@.id==2)].description").value(ability2.getDescription()));
    }
}
