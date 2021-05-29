package grapp.grapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

@SpringBootTest
public class PrendaGR3Test {
   
   private static DataSource dataS = ConnectForTests.getConnect().getDataSource();
   //---------------Test unitarios---------------
   
   @Test
   @Order(1)
   public void ValidarAtributos(){
      //-------------------------------------------------
     
      FileInputStream fis = null;
      try {
         fis = new FileInputStream("src/main/resources/static/images/conjuntos/conjuntoEjemplo.png");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      MockMultipartFile foto = null;
      try {
         foto = new MockMultipartFile("file", "conjuntoEjemplo.png", "image/jpeg", fis);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      Prenda ropa = new Prenda("Ropa valida8()","noseque@algo.com", "url ropa", "Descripcion valida", foto);
      assertNull(ropa.comprobarDatos()); 
        
      ropa = new Prenda("Ropa no valida, tamaño del nombre mayor que 50 xxxx","noseque@algo.com", "url ropa", "Descripcion valida", foto);
      assertEquals(ropa.comprobarDatos(), "El nombre no puede tener mas de 50 caracteres."); 

      ropa = new Prenda("Ropa no valida, caracteres incorrecots *+[#","noseque@algo.com", "url ropa", "Descripcion valida", foto);
      assertEquals(ropa.comprobarDatos(), "El nombre contiene caracteres invalido, deben ser letras, numeros y ()");
      
      ropa = new Prenda("Ropa no valida","noseque@algo.com", "url ropa", "Descripcion no valida xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", foto);
      assertEquals(ropa.comprobarDatos(), "La descripcion no puede tener mas de 280 caracteres."); 

      ropa = new Prenda("","noseque@algo.com", "url ropa", "Descripcion no valida ", foto);
      assertEquals(ropa.comprobarDatos(), "El campo nombre no esta rellenado"); 
      
      //-------------------------------------------------

      try {
         fis = new FileInputStream("src/test/java/grapp/grapp/resources/InvalidFile.txt");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      MockMultipartFile txt = null;
      try {
         txt = new MockMultipartFile("file", "test.txt", "text/plain", fis);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      ropa = new Prenda("Foto no valida","noseque@algo.com", "url ropa", "Descripcion no valida ", txt);
      assertEquals(ropa.comprobarDatos(), "El tipo de archivo debe ser jpg o png"); 

      //-------------------------------------------------

      try {
         fis = new FileInputStream("src/test/java/grapp/grapp/resources/Initial D - Deja Vu.mp4");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      MockMultipartFile video = null;
      try {
         video = new MockMultipartFile("file", "Initial D - Deja Vu.mp4", "video/mp4", fis);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      ropa = new Prenda("archivo grande","noseque@algo.com", "url ropa", "Descripcion no valida ", video);
      assertEquals(ropa.comprobarDatos(), "El tamaño del archivo no puede pasar mas de 5MB"); 

        
   }

      
   @Test
   @Order(2)
   public void InsertarPrenda(){
   
      Prenda ropa = new Prenda();
      assertEquals(ropa.insertPrenda("Ropa valida8()", "asdasd@asd.es", "Descripcion valida", "ropa.jpg", dataS), "Prenda insertada correctamente");        
     
   }

   @AfterAll
   public static void BorrarDatos(){

      String query = "delete from PRENDAS where propietario = 'asdasd@asd.es' AND NOMBRE = 'Ropa valida8()'";
      PreparedStatement preparedStmt = null;
      
      try {
          preparedStmt = dataS.getConnection().prepareStatement(query);
            try{
                preparedStmt.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }finally{
                preparedStmt.close();
            }  
      } catch (SQLException e) {
          e.printStackTrace();
          
      }
        
     
   }

}
