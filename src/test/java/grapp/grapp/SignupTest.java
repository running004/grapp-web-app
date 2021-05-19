package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@Sql("/test-myjdbc.sql")
public class SignupTest {

	private static DataSource dataS = Connect.getConnect().getDataSource();
    

    @Test
    @Order(1)
    public void usuarioValido() throws SQLException{
        User usuarioValido = new User("test@test.test", "12345678", "12345678");

        //realizamos todas las pruebas con un usuario valido, que no exista en la base de datos y se inserte correctamente
        assertEquals(null, usuarioValido.comprobarDatos());
        assertEquals(false, usuarioValido.searchUser(dataS));
        assertEquals("Usuario insertado correctamente", usuarioValido.insertUser(dataS));
    }

    @Test   
    @Order(2)
    public void usuarioNoValido(){
        //probamos la comprobacion del correo no valido
        User usuarioNoValido = new User("ae", "12345678", "12345678");
        assertNotEquals(null, usuarioNoValido.comprobarDatos());

        //probamos la comprobacion de contraseña no valida
        usuarioNoValido = new User("elmillor@payaso.cum", "123", "123");
        assertNotEquals(null, usuarioNoValido.comprobarDatos());

        //probamos la compracion de contraseña distinta
        usuarioNoValido = new User("elmillor@payaso.cum", "12345678", "123987654");
        assertNotEquals(null, usuarioNoValido.comprobarDatos());

        //probamos recoger bien las excepciones SQL
        usuarioNoValido = new User(null, null, null);
        assertNotEquals("Usuario insertado correctamente", usuarioNoValido.insertUser(dataS));
        }
        
    @AfterAll
    public static void borradoDatos(){
        //borramos el usuario insertado correctamente
        String query = "delete from usuarios where email = 'test@test.test'";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }
}
