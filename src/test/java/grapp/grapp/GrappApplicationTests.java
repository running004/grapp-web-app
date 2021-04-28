package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrappApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Im testing");
		assertTrue(true);
		assertEquals(1,1);
	}
	
	@Test
	void testUnitGenerateIdImg(){
		try{
			String generatedId = UnitTestUpload.UnitGenerateIdImg();
			assertNotEquals(generatedId, "Hubo un fallo");
		}catch(Exception e){
			System.out.println(e.getMessage());
			assertTrue(false);
		}	
	}

	@Test
	void testUnitGenerateURLImg(){
		try{
			String resultadoUrl = UnitTestUpload.UnitGenerateURLImg("GWI0E4f");
			assertEquals(resultadoUrl, "https://i.imgur.com/GWI0E4f.jpg");
		}catch(Exception e){
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

	@Test
	void testIntegrationUploadImg(){
		try{
        String generatedId = UnitTestUpload.UnitGenerateIdImg();
        String resultadoUrl = UnitTestUpload.UnitGenerateURLImg(generatedId);
		assertEquals(resultadoUrl, "https://i.imgur.com/"+generatedId+".jpg");
		}catch(Exception e){
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}

	@Test
	void testIntegrationUserValidation(){
		User usuario = new User("jpcarrera@ucm.es", "1234", "1234");
		try{
			boolean ok = usuario.comprobarDatos();
        	assertTrue(ok);
		}catch(Exception e){
			System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
}
