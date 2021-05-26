package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class SearchClothesNameTest {
    private static DataSource dataS = ConnectForTests.getConnect().getDataSource();

    @Test
    @Order(1)
    public void usuarioPrendasValidos() throws SQLException{

        User usuarioValido = new User("FranCuesta@ucm.es", "12345678", "12345678");
        Prenda valida = new Prenda("zapatos", "FranCuesta@ucm.es", "realizando prueba", "imagen");
        BusquedaPrenda bp = new BusquedaPrenda();

        //realizamos todas las pruebas con un usuario valido y una prenda valida, que no existan en la base de datos y se inserten correctamente
        usuarioValido.insertUser(dataS);
        valida.insertPrenda("zapatos", "FranCuesta@ucm.es", "realizando prueba", "imagen", dataS);

        //Comenzamos comprobando el metodo que valida que existe un usuario
        assertTrue(bp.validarExisteUsuario(usuarioValido.getEmail(), dataS));

        //Aquí se rellena la lista con las prendas que posea el usuario, probamos que exista alguna
        assertTrue(bp.rellenarPorNombre(valida.getnombre(), dataS));

        //Aqui comprobamos la llamada a ambos metodos que debe retornar null en caso de no existir ningún error
        assertNull(bp.BuscarPorNombre(valida.getnombre(), dataS));
    }


    @Test   
    @Order(2)
    public void busquedaNoValida(){
        
        //Insertamos un usuario que no tenga prendas
        User usuarioSinPrendas = new User("FidelCastro@ucm.es", "12345678", "12345678");
        usuarioSinPrendas.insertUser(dataS);
        
        Prenda noValida = new Prenda("camiseta9", "FidelCastro@ucm.es", "bit.ly/3vg0cyB", "zapatosGucci");
        Prenda noExiste = new Prenda("alzacuellos", "FidelCastro@ucm.es", "bit.ly/3vg0cyB", "zapatosGucci");

        BusquedaPrenda bp = new BusquedaPrenda();

        assertEquals("El formato del nombre de la prenda no es valido", bp.BuscarPorNombre( noValida.getnombre(), dataS));
        assertEquals("No existen prendas con este nombre", bp.BuscarPorNombre( noExiste.getnombre(), dataS));
        }

    @AfterAll
    public static void borradoDatos(){
        //borramos los usuarios y la prenda insertados correctamente
        String query = "delete from prendas where propietario = 'FranCuesta@ucm.es'";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();
            query = "delete from usuarios where email = 'FranCuesta@ucm.es'";
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();
            query = "delete from usuarios where email = 'FidelCastro@ucm.es'";
            preparedStmt = dataS.getConnection().prepareStatement(query);
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
            
    }
}
