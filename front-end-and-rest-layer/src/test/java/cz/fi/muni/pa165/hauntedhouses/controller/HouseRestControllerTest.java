package cz.fi.muni.pa165.hauntedhouses.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.configuration.Uri;
import cz.muni.fi.pa165.hauntedhouses.controller.HouseRestController;
import cz.muni.fi.pa165.hauntedhouses.dto.CursedObjectDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.MonsterDTO;
import cz.muni.fi.pa165.hauntedhouses.enums.MonsterAttractionFactor;
import cz.muni.fi.pa165.hauntedhouses.exceptions.NoEntityException;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Filip Petrovic (422334)
 */
@WebAppConfiguration
@ContextConfiguration(classes = RestContextConfiguration.class)
public class HouseRestControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private HouseFacade houseFacade;

    @Inject
    @InjectMocks
    private HouseRestController houseController;
        
    @Inject
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    private HouseDTO house1;
    private HouseDTO house2;

    private MonsterDTO monster;
    
    private CursedObjectDTO cursedObject;
    
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(houseController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();          
    }
    
    @BeforeMethod
    public void initEntities(){
        house1 = new HouseDTO();
        house1.setId(1l);
        house1.setName("Forlorn Rowe");
        house1.setAddress("Raven Hill");
        house1.addMonsterId(1l);
        
        house2 = new HouseDTO();
        house2.setId(2l);
        house2.setName("Xardas' Tower");
        house2.setAddress("It's been too long, I don't even remember");
        house2.addCursedObjectId(1l);

        monster = new MonsterDTO();
        monster.setId(1l);
        monster.setName("Unicorn");
        monster.setDescription("PERSISTENCE UNICORN IS BACK!!!!");
        monster.setHauntedIntervalStart(LocalTime.of(1, 15));
        monster.setHauntedIntervalEnd(LocalTime.of(5, 30));
        monster.setHouseId(1l);
        
        cursedObject = new CursedObjectDTO();
        cursedObject.setId(1l);
        cursedObject.setName("Random trash");
        cursedObject.setDescription("Seemingly harmless");
        cursedObject.setMonsterAttractionFactor(MonsterAttractionFactor.LOW);
        cursedObject.setHouseId(2l);
    }
    
    @Test
    public void createHouse() throws Exception {
        HouseCreateDTO newHouse = new HouseCreateDTO();
        newHouse.setName("Shiny");
        newHouse.setAddress("Shiny Town");

        when(houseFacade.createHouse(any(HouseCreateDTO.class))).thenReturn(1l);
        String json = this.convertObjectToJsonBytes(newHouse);

        mockMvc.perform(post("/houses/create").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void getValidHouse() throws Exception {
        when(houseFacade.getHouseById(1l)).thenReturn(house1);
        when(houseFacade.getHouseById(2l)).thenReturn(house2);

        mockMvc.perform(get("/houses/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value("Forlorn Rowe"));
        mockMvc.perform(get("/houses/id/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value("Xardas' Tower"));
    }

    @Test
    public void getInvalidHouse() throws Exception {
        when(houseFacade.getHouseById(3l)).thenThrow(new NoEntityException("Nothing to be had here."));

        mockMvc.perform(get("/houses/id/3"))
            .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getAllHouses() throws Exception {
        when(houseFacade.getAllHouses()).thenReturn(Arrays.asList(house1, house2));
        
        mockMvc.perform(get(Uri.HOUSES))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[?(@.id==1)].name").value("Forlorn Rowe"))
            .andExpect(jsonPath("$.[?(@.id==1)].address").value("Raven Hill"))
            //.andExpect(jsonPath("$.[?(@.id==1)].monsterIds").value(house1.getMonsterIds()))
            .andExpect(jsonPath("$.[?(@.id==2)].name").value("Xardas' Tower"))
            .andExpect(jsonPath("$.[?(@.id==2)].address").value("It's been too long, I don't even remember"));
            //.andExpect(jsonPath("$.[?(@.id==2)].cursedObjectIds").value(house2.getCursedObjectIds()));
    }
        
    private static String convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
