package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	void testUnitUploadId(){
		UnitTestUpload test = new UnitTestUpload();
		try{
			test.setUp();
			test.IdInsertTest();
		}catch(Exception e){

		}
	}
	/*
	@Test
	void testUnitUploadImg(){
		UnitTestUpload test = new UnitTestUpload();
		try{
			test.ImgInsertTest();
		}catch(Exception e){}
	}
	*/

}
