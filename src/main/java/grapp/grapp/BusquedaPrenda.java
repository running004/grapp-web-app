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
    public Boolean BuscarPorNombre(String nombre){

         return true;
    }
    
    public String BuscarPorUsuario(String nombre,DataSource dataSource){ // cambiarlo a strings
       if(validarExisteUsuario(nombre,dataSource)) return "Este usuario no existe";
       if(rellenarPorNombre(nombre,dataSource)) return "El usuario no tiene subida ninguna prenda";
       // buscar en la tabla de prendas por ese usario y rellenar la lista
        return "";
    }
    
    public Boolean BuscarMiArmarioTodo(String nombre){

        return true;
    }
    
    public Boolean BuscarMiArmarioPorNombre(String nombre){

        return true;
    }
    
    public boolean validarExisteUsuario(String emailUser, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USUARIOS WHERE emailUser='"+emailUser+"' ");
            if(rs.next()) encontrado = true;
        } catch(Exception e){
            System.out.println("No existe este usuario.");
        }
        return encontrado;
    }
    public boolean rellenarPorNombre(String emailUser, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE emailUser='"+emailUser+"' ");
            if(rs.next()) encontrado = true;
            
            //rellenar la lista que no seeeee wiiiiii

        } catch(Exception e){
            System.out.println("No existe este usuario.");
        }
        return encontrado;
    }

    /*
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