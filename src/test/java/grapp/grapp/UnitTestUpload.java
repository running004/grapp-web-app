package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class UnitTestUpload {
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
    @Disabled
	public void IdInsertTest()throws Exception{
        when(formulario.getText()).thenReturn("Prueba Pepe");
        mockMvc.perform(MockMvcRequestBuilders.post("/upload"))
        .andExpect(MockMvcResultMatchers.model().attribute("userID", "Prueba Pepe"));
       
    }
    /*
    @Test
	public void ImgInsertTest()throws Exception{
        File file = new File("E://Shared Data/Images/xyz.jpg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
            fileItem.getName(), "image/png", IOUtils.toByteArray(input));
        when(formulario.getImg()).thenReturn("Prueba Pepe");
        mockMvc.perform(MockMvcRequestBuilders.get("/upload"))
        .andExpect(MockMvcResultMatchers.model().attribute("userID", "Prueba Pepe"));
       
    }
    */
}
