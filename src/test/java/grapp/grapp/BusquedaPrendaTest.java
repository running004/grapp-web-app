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
        // borrar();
        User u = new User("JuanCuesta@gmail.es", "12345678", "12345678");
        u.insertUser(data);
        //u.searchUser(data);
        Prenda prenda = new Prenda("zapatos", "JuanCuesta@gmail.es", "bit.ly/3vg0cyB", "zapatosGucci");
        prenda.insertPrenda(prenda.getnombre(), prenda.getemailUser(), prenda.getdescripcion(),
             prenda.getfoto(), data);
        // prenda.searchPrendaPorNombre(prenda.getnombre(), data);
        BusquedaPrenda busq = new BusquedaPrenda(prenda.getnombre(), prenda.getemailUser());
        assertEquals("", busq.BuscarPorNombre(prenda.getnombre(), data));
     }
 
     @Test
     @Order(2)
     public void buscarNoPrendaValida(){
        Prenda prendaNoValida = new Prenda("camiseta9", "JoseCuesta@gmail.es", "bit.ly/3vg0cyB", "zapatosGucci");
        Prenda userMal = new Prenda("alzacuellos", "JoseCuesta@gmail.es", "bit.ly/3vg0cyB", "zapatosGucci");
        
        BusquedaPrenda busqNoValida = new BusquedaPrenda(prendaNoValida.getnombre(), prendaNoValida.getemailUser());
        BusquedaPrenda busqUserMal = new BusquedaPrenda(userMal.getnombre(), userMal.getemailUser());
        assertEquals("El formato del nombre de la prenda no es valido", 
            busqNoValida.BuscarPorNombre(prendaNoValida.getnombre(), data));
        assertEquals("No existen prendas con este nombre", 
            busqUserMal.BuscarPorNombre(userMal.getnombre(), data));
     }

    @AfterAll
     public static void borrar(){
         //borramos el usuario insertado correctamente
         String query = "delete from prendas where propietario  = 'JuanCuesta@gmail.es'";
         PreparedStatement preparedStmt;
         // PreparedStatement preparedStmt;
         try {
             preparedStmt = data.getConnection().prepareStatement(query);
             preparedStmt.execute();
             query = "delete from usuarios where email = 'JuanCuesta@gmail.es'";
             preparedStmt = data.getConnection().prepareStatement(query);
             preparedStmt.execute();

         } catch (SQLException e) {
             e.printStackTrace();
         }
             
     }

}
