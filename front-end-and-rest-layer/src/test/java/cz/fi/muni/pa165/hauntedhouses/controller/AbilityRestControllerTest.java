package cz.fi.muni.pa165.hauntedhouses.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.controller.AbilityRestController;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import java.io.IOException;
import java.util.Arrays;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
 * @version 18.12.2016
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

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvcMocker = standaloneSetup(abilityRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @BeforeMethod
    public void initEntities() {
        ability1 = new AbilityDTO();
        ability1.setId(1L);
        ability1.setName("Ability 1");
        ability1.setDescription("Ability 1 description");

        ability2 = new AbilityDTO();
        ability2.setId(2L);
        ability2.setName("Ability 2");
        ability2.setDescription("Ability 2 description");

        when(abilityFacade.getAbilityById(1L)).thenReturn(ability1);
        when(abilityFacade.getAbilityById(2L)).thenReturn(ability2);

        when(abilityFacade.getAllAbilities()).thenReturn(Arrays.asList(ability1, ability2));
    }

    @Test
    public void createAbilityTest() throws Exception {
        AbilityCreateDTO abilityCreateDTO = new AbilityCreateDTO();
        abilityCreateDTO.setName("Ability name");
        abilityCreateDTO.setDescription("Ability description");

        AbilityDTO abilityDTO = new AbilityDTO();
        abilityDTO.setName("Ability name");
        abilityDTO.setDescription("Ability description");

        when(abilityFacade.createAbility(abilityCreateDTO)).thenReturn(1L);
        when(abilityFacade.getAbilityById(1L)).thenReturn(abilityDTO);
        mvcMocker.perform(
                post(Uri.ABILITIES + "/create").contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(abilityCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(abilityDTO.getName()))
                .andExpect(jsonPath("$.description").value(abilityDTO.getDescription()));
    }

    @Test
    public void updateAbilityTest() throws Exception {
        ability1.setName("Ability 1 name changed");

        mvcMocker.perform(put(Uri.ABILITIES + "/1").contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(ability1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(ability1.getName()))
                .andExpect(jsonPath("$.description").value(ability1.getDescription()));
    }

    @Test
    public void deleteAbilityTest() throws Exception {
        doNothing().when(abilityFacade).deleteAbility(1L);
        mvcMocker.perform(delete(Uri.ABILITIES + "/1")).andExpect(status().isOk());
    }

    @Test
    public void getAbilityByIdTest() throws Exception {
        mvcMocker.perform(get(Uri.ABILITIES + "/id/1")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value(ability1.getName()))
                .andExpect(jsonPath("$.[?(@.id==1)].description").value(ability1.getDescription()));

        mvcMocker.perform(get(Uri.ABILITIES + "/id/2")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value(ability2.getName()))
                .andExpect(jsonPath("$.[?(@.id==2)].description").value(ability2.getDescription()));
    }

    @Test
    public void getInvalidHouse() throws Exception {
        when(abilityFacade.getAbilityById(3L)).thenThrow(new NoEntityException("No ability found, correct."));
        mvcMocker.perform(get(Uri.ABILITIES + "/id/3")).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllAbilitiesTest() throws Exception {
        mvcMocker.perform(get(Uri.ABILITIES))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value(ability1.getName()))
                .andExpect(jsonPath("$.[?(@.id==1)].description").value(ability1.getDescription()))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value(ability2.getName()))
                .andExpect(jsonPath("$.[?(@.id==2)].description").value(ability2.getDescription()));
    }

    private static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
