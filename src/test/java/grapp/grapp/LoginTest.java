package grapp.grapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LoginTest{

    private DataSource dataSource;
    
    @Test
    public void usuarioRegistrado(){
        User usuarioRegistrado = new User("elmillor@payaso.es", "12345678", "12345678");
        assertEquals(true, usuarioRegistrado.searchUser(dataSource));
    }

    @Test
    public void usuarioNoRegistrado(){
        User noRegistrado = new User("loquesea@mk.es", "12345678", "12345678");
        assertNotEquals(true, noRegistrado.searchUser(dataSource));

    }
    
}
