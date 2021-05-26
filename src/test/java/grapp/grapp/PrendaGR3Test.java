package grapp.grapp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrendaGR3Test {
   
   private static DataSource dataS = Connect.getConnect().getDataSource();
   //---------------Test unitarios---------------
   
   @Test
   @Order(1)
   public void ValidarAtributos(){
   
      Prenda ropa = new Prenda("Ropa valida8()","noseque@algo.com", "url ropa", "Descripcion valida");
      assertNull(ropa.comprobarDatos()); 
        
      ropa = new Prenda("Ropa no valida, tamaño del nombre mayor que 50 xxxx","noseque@algo.com", "url ropa", "Descripcion valida");
      assertEquals(ropa.comprobarDatos(), "El nombre no puede tener mas de 50 caracteres."); 

      ropa = new Prenda("Ropa no valida, caracteres incorrecots *+[#","noseque@algo.com", "url ropa", "Descripcion valida");
      assertEquals(ropa.comprobarDatos(), "El nombre contiene caracteres invalido, deben ser letras, numeros y ()");
      
      ropa = new Prenda("Ropa no valida","noseque@algo.com", "url ropa", "Descripcion no valida xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
      assertEquals(ropa.comprobarDatos(), "La descripcion no puede tener mas de 280 caracteres."); 

      ropa = new Prenda("","noseque@algo.com", "url ropa", "Descripcion no valida ");
      assertEquals(ropa.comprobarDatos(), "El campo nombre no esta rellenado"); 

        
   }

      
   @Test
   @Order(2)
   public void InsertarPrenda(){
   
      Prenda ropa = new Prenda();
      assertEquals(ropa.insertPrenda("Ropa valida8()", "asdasd@asd.com", "Descripcion valida", "ropa.jpg", dataS), "Prenda insertada correctamente");        
     
   }

   @AfterAll
   public static void BorrarDatos(){

      String query = "delete from PRENDAS where propietario = 'asdasd@asd.com' AND NOMBRE = 'Ropa valida8()'";
      PreparedStatement preparedStmt;
      
      try {
          preparedStmt = dataS.getConnection().prepareStatement(query);
          preparedStmt.execute();
      } catch (SQLException e) {
          e.printStackTrace();
      }
        
     
   }

}
