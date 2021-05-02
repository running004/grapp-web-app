package grapp.grapp;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UnitTestUser {

	@Mock
    private User usuario;

    @InjectMocks
    private appController controller;
    private MockMvc mockMvc;


	
    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    /*@Test
    public void searchUser()throws Exception{
        when(usuario.searchUserForSingUp("termostato_7@hotmail.com", null)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
        .andExpect(status().isOk());
    }*/

    @Test
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
    }
	
}