package cz.fi.muni.pa165.hauntedhouses.controller;

import cz.muni.fi.pa165.hauntedhouses.configuration.RestContextConfiguration;
import cz.muni.fi.pa165.hauntedhouses.controller.HouseRestController;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import javax.inject.Inject;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

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
        
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(houseController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();          
    }
}
