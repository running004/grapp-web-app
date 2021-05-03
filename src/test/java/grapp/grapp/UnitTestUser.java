package grapp.grapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
public class UnitTestUser {

	@Mock
    private User usuarioValido;
    @Mock
    private User usuarioNoValido;

    @InjectMocks
    private appController controller;
    private MockMvc mockMvc;


	
    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(usuarioValido.validarMail()).thenReturn(true);
        when(usuarioNoValido.validarMail()).thenReturn(false);
        when(usuarioValido.comprobarDatos()).thenReturn(null);
        when(usuarioNoValido.comprobarDatos()).thenReturn("Completa todos los campos");
    }

    /*@Test
    public void searchUser()throws Exception{
        when(usuario.searchUserForSingUp("termostato_7@hotmail.com", null)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
        .andExpect(status().isOk());
    }*/

    @Test
	public void InsertValido()throws Exception{
        User u = new User();
        u.setEmail("1");

        Gson gson = new Gson();
        String json = gson.toJson(u);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(MockMvcResultMatchers.view().name("login.html"));

    }
    /* @Test
	public void IdInsertTest()throws Exception{
		User usuario = Mockito.mock(User.class);
        when(usuario.getEmail()).thenReturn("");
		when(usuario.getContrasenia()).thenReturn("");
		when(usuario.getContraseniaRepetida()).thenReturn("");
		when(usuario.comprobarDatos()).thenReturn(false);
		when(usuario.validarMail()).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup"))
		.andExpect(MockMvcResultMatchers.view().name("signup.html"))
        .andExpect(MockMvcResultMatchers.model().attribute("correoMal", true));
    } */
	
    
}
