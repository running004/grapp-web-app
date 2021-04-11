package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UnitTestUpload {
    /*
    @Mock
    private formulario formulario;

    @InjectMocks
    private appController controller;
    private MockMvc mockMvc;
	
    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
	public void IdInsertTest()throws Exception{
        when(formulario.getText()).thenReturn("Prueba Pepe");
        mockMvc.perform(MockMvcRequestBuilders.get("/upload"))
        .andExpect(MockMvcResultMatchers.model().attribute("userID", "Prueba Pepe"));
    }
    */
    static String UnitGenerateIdImg()throws Exception{
        File file = new File("src/main/resources/static/images/conjuntos/conjuntoEjemplo.png"); 
        FileInputStream input = new FileInputStream(file); 
        MultipartFile multipartImg = new MockMultipartFile("image", file.getName(), "image/png", input);
        String generatedId = imgUrlScraper.uploadImg(multipartImg);
        return generatedId;
   }
    static String UnitGenerateURLImg(String generatedId)throws Exception{  
        String resultadoUrl = imgUrlScraper.getImageUrl(generatedId);
        return resultadoUrl;
   }
}
