package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


import javax.sql.DataSource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/test-myjdbc.sql")
public class LogInTest{
    private static DataSource dataS = ConnectForTests.getConnect().getDataSource();
    @Test
    @Order(1)
    public void usuarioRegistrado(){
        User usuarioRegistrado = new User("hola@cum.es", "12345678", "12345678");
        assertEquals(true, usuarioRegistrado.searchUser(dataS));
    }
    @Test
    @Order(2)
    public void usuarioNoRegistrado(){
        User usuarioNoRegistrado = new User("loquesea@mk.es", "12345678", "12345678");
        assertNotEquals(true, usuarioNoRegistrado.searchUser(dataS));
    }


}
