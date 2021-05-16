package grapp.grapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class BusquedaPrenda
{
    private List<Prenda> miLista ;
    public BusquedaPrenda(){
        miLista = new ArrayList<Prenda>();
    }
    public Boolean BuscarPorNombre(String nombre, DataSource dataSource){
        boolean existe = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE nombre="+nombre+" ");
            if(rs.next()){
                if(rs.getInt(1) != 0)
                    existe = true;
            } 
        } catch(Exception e){
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        return existe;
    }
    
    public Boolean BuscarPorUsuario(String nombre){

        return true;
    }
    
    public Boolean BuscarMiArmarioTodo(String nombre){

        return true;
    }
    
    public Boolean BuscarMiArmarioPorNombre(String nombre){

        return true;
    }
    /*
    public String insertUser(String emailUser, String foto, DataSource dataSource){
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            stmt.executeQuery("INSERT INTO USUARIOS VALUES ("+ emailUser + ", " + hashfoto(foto) +")");
            return "Usuario insertado correctamente";
        } catch(Exception e){
            return "Fallo al insertar usuario, recuerde que debe ser un correo válido y la contraseña como mínimo debe tener 8 caracteres";
        }

    }
    public boolean searchUser(String emailUser, String foto, DataSource dataSource){
        boolean logueado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USUARIOS WHERE emailUser='"+emailUser+"' AND foto='"+foto+"' ");
            if(rs.next()) logueado = true;
        } catch(Exception e){
            System.out.println("Fallo al loguearse, recuerde que debe ser un correo válido y la contraseña como mínimo debe tener 8 caracteres");
        }
        return logueado;
    }

    
    public boolean searchUserForSingUp(String emailUser, DataSource dataSource){
        boolean existe = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USUARIOS WHERE emailUser='"+emailUser+"' ");
            if(rs.next()) existe = true;
        } catch(Exception e){
            System.out.println("Usuario ya existente");
        }
        return existe;
    }
    */
}