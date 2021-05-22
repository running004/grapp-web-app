package grapp.grapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PrendaGR3Test {

     //---------------Test unitarios---------------
     @Test
     public void ValidarAtributos(){
      
        Prenda p = new Prenda("Ropa valida","noseque@algo.com", "url ropa", "Descripcion valida");
        assertNull(p.comprobarDatos()); 
        
        p = new Prenda("Ropa no valida, tamaño del nombre mayor que 50 xxxx","noseque@algo.com", "url ropa", "Descripcion valida");
        assertEquals(p.comprobarDatos(), "El nombre no puede tener mas de 50 caracteres."); 

        p = new Prenda("Ropa no valida, caracteres incorrecots *+[#","noseque@algo.com", "url ropa", "Descripcion valida");
        assertEquals(p.comprobarDatos(), "El nombre contiene caracteres invalido, deben ser [A-Za-z0-9()]"); 

        p = new Prenda("Ropa no valida","noseque@algo.com", "url ropa", "Descripcion no valida xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        assertEquals(p.comprobarDatos(), "La descripcion no puede tener mas de 280 caracteres."); 

        
     }
}
