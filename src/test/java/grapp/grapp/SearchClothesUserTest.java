package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@Sql("/test-myjdbc.sql")
public class SearchClothesUserTest {

	private static DataSource dataS = ConnectForTests.getConnect().getDataSource();
    

    @Test
    @Order(1)
    public void usuarioPrendasValidos() throws SQLException{

        User usuarioValido = new User("test@test.test", "12345678", "12345678");
        Prenda prendaValida = new Prenda();
        BusquedaPrenda bp = new BusquedaPrenda();

        //realizamos todas las pruebas con un usuario valido y una prenda valida, que no existan en la base de datos y se inserten correctamente
        usuarioValido.insertUser(dataS);
        prendaValida.insertPrenda("camiseta", "test@test.test", "Esta prenda es unicamente de prueba", "imagen", dataS);

        //Comenzamos comprobando el metodo que valida que existe un usuario
        /*assertTrue(bp.validarExisteUsuario(usuarioValido.getEmail(), dataS));

        //Aquí se rellena la lista con las prendas que posea el usuario, probamos que exista alguna
        assertTrue(bp.rellenarPorUsuario(usuarioValido.getEmail(), dataS));

        //Aqui comprobamos la llamada a ambos metodos que debe retornar null en caso de no existir ningún error
        assertNull(bp.BuscarPorUsuario( usuarioValido.getEmail(), dataS));
        */
    }


    @Test   
    @Order(2)
    public void busquedaNoValida(){
        
        //Insertamos un usuario que no tenga prendas
        User usuarioSinPrendas = new User("noValido@noValido.not", "12345678", "12345678");
        usuarioSinPrendas.insertUser(dataS);


        BusquedaPrenda bp = new BusquedaPrenda();

        //probamos la comprobacion del usuario no existente
        /*assertNotEquals(true, bp.validarExisteUsuario("NoExiste", dataS));

        //probamos que el usuario no tiene prendas
        assertNotEquals(true, bp.rellenarPorUsuario(usuarioSinPrendas.getEmail(), dataS));

        //probamos el metodo completo que comprueba el usuario y las prendas que tiene para ver los mensajes que retorna
        
        //aqui probamos que mensaje retorna si no tiene prendas
        assertEquals("El usuario no tiene subida ninguna prenda", bp.BuscarPorUsuario( usuarioSinPrendas.getEmail(), dataS));

        //probamos un usuario que no existe en el metodo completo
<<<<<<< HEAD
        assertNotEquals("Este usuario no existe", bp.BuscarPorUsuario( "noExisto", dataS));
        */
    }
=======
        assertEquals("Este usuario no existe", bp.BuscarPorUsuario( "noExisto", dataS));
        }
>>>>>>> 560c77458da9db2ea217704e33928dd502df1f65
        
    @AfterAll
    public static void borradoDatos(){
        //borramos los usuarios y la prenda insertados correctamente
        String query = "delete from prendas where propietario = 'test@test.test'";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();
            query = "delete from usuarios where email = 'test@test.test'";
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();
            query = "delete from usuarios where email = 'noValido@noValido.not'";
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }
}
