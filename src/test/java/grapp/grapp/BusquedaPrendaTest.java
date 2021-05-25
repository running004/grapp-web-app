package grapp.grapp;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Sql("/test-myjdbc.sql")
public class BusquedaPrendaTest {
    
    private static DataSource data = Connect.getConnect().getDataSource();

     //prueba de buscar prenda por nombre
     @Test
     public void buscarPrendaValida(){
        User u = new User("megaJuan991@gmail.es", "12345678", "12345678");
       /* u.insertUser(data);*/
        u.searchUser(data);
        Prenda prenda = new Prenda("zapatos", "megaJuan991@gmail.es", "bit.ly/3vg0cyB", "zapatosGucci");
       /* prenda.insertPrenda(prenda.getnombre(), prenda.getemailUser(), prenda.getdescripcion(),
             prenda.getfoto(), data);*/
        prenda.searchPrendaPorNombre(prenda.getnombre(), data);
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

    /* @AfterAll
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
             
     }*/

}
