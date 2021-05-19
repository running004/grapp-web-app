package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@Sql("/test-myjdbc.sql")
public class SignupTest {

	private DataSource dataS = Connect.getConnect().getDataSource();
    

    @Test
    @Order(1)
    public void usuarioValido() throws SQLException{
        User usuarioValido = new User("elmillor@payaso.es", "12345678", "12345678");
        assertEquals(null, usuarioValido.comprobarDatos());
        assertEquals(false, usuarioValido.searchUser(dataS));
    }

    @Test   
    @Order(2)
    public void usuarioNoValido(){
        User correoMal = new User("ae", "12345678", "12345678");
        User contraseniaMal = new User("elmillor@payaso.cum", "123", "123");
        User contraseniaNoCoincide = new User("elmillor@payaso.cum", "12345678", "123987654");
        assertNotEquals(null, correoMal.comprobarDatos());
        assertNotEquals(null, contraseniaMal.comprobarDatos());
        assertNotEquals(null, contraseniaNoCoincide.comprobarDatos());
        
        }
    
}
