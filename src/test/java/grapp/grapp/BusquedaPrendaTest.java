package grapp.grapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

@SpringBootTest
@Sql("/test-myjdbc.sql")
public class BusquedaPrendaTest {
    
    private static DataSource data = Connect.getConnect().getDataSource();

     //prueba de buscar prenda por nombre
     @Test
     public void buscarPrendaValida(){
         // DataSource data = Connect.getConnect().getDataSource();
        User u = new User("prueba@gmail.es", "12345678", "12345678");
        u.insertUser(data);
        Prenda prenda = new Prenda("camiseta", "prueba@gmail.es", "nosequeponeraqui", "fotodecamiseta");
        prenda.insertPrenda(prenda.getnombre(), prenda.getemailUser(), prenda.getdescripcion(),
             prenda.getfoto(), data);
        BusquedaPrenda busq = new BusquedaPrenda();
        assertEquals("", busq.BuscarPorNombre(prenda.getnombre(), data));
     }
 
     @Test
     @Order(2)
     public void buscarNoPrendaValida(){
        Prenda prendaNoValida = new Prenda("camiseta9", "elmillor@payaso.es", "nosequeponeraqui", "fotodecamiseta");
        Prenda userMal = new Prenda("jersey", "noexiste@ucm.es", "nosequeponeraqui", "fotodecamiseta");
        BusquedaPrenda busq = new BusquedaPrenda();
        assertEquals("El formato del nombre de la prenda no es valido", 
            busq.BuscarPorNombre(prendaNoValida.getnombre(), data));
        assertEquals("No existen prendas con este nombre", 
            busq.BuscarPorNombre(userMal.getnombre(), data));
     }

     @AfterAll
     public static void borradoDatos(){
         //borramos el usuario insertado correctamente
         String query = "delete from usuarios where email = 'test@test.test'";
         PreparedStatement preparedStmt;
         try {
             preparedStmt = data.getConnection().prepareStatement(query);
             preparedStmt.execute();
         } catch (SQLException e) {
             e.printStackTrace();
         }
             
     }

}
