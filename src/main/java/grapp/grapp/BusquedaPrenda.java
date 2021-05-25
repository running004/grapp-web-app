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
    private String nombre, usuario;
    public BusquedaPrenda(){}
    public BusquedaPrenda(String nombre, String usuario){
        miLista = new ArrayList<Prenda>();
        this.nombre=nombre;
        this.usuario=usuario;

    }
    public List<Prenda> getLista(){
        return this.miLista;
    }
    public String getNombre(){
        return nombre;
    }
    public String getUsuario(){
        return usuario;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    public String BuscarPorUsuario(String nombre,DataSource dataSource){ // cambiarlo a strings
        if(validarExisteUsuario(nombre,dataSource)) return "Este usuario no existe";
        if(rellenarPorUsuario(nombre,dataSource)) return "El usuario no tiene subida ninguna prenda";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return null;
     }
     public String BuscarPorNombre(String nombre,DataSource dataSource){ // cambiarlo a strings
        if(!validarNombrePrenda(nombre, dataSource)) return "El formato del nombre de la prenda no es valido";
        if(!rellenarPorNombre(nombre,dataSource)) return "No existen prendas con este nombre";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return "";
     }
     public String BuscarPorNombreyUsuario(String nombre,String emailUser, DataSource dataSource){ // cambiarlo a strings
        if(rellenarPorNombreyUsuario(nombre,emailUser,dataSource)) return "El nombre y/o el usuario no existe";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return "";
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
    public boolean rellenarPorUsuario(String emailUser, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE emailUser='"+emailUser+"' ");
                 while(rs.next()){
                    Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("foto") ,rs.getString("descripcion"),rs.getString("emailUser"));
                    miLista.add(aux);
                    encontrado = true;
            }
            //rellenar la lista que no seeeee wiiiiii

        } catch(Exception e){
            System.out.println("No existe este usuario.");
        }
        return encontrado;
    }


    public Boolean rellenarPorNombre(String nombre, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE nombre='"+nombre+"' ");
            while(rs.next()){
                Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("propietario") ,rs.getString("imgurl"),rs.getString("descripcion"));
                miLista.add(aux);
                encontrado = true;
        }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        return encontrado;
    }
    public Boolean rellenarPorNombreyUsuario(String nombre,String emailUser, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE nombre="+nombre+"' AND emailUser='"+emailUser+"' ");
            while(rs.next()){
                Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("foto") ,rs.getString("descripcion"),rs.getString("emailUser"));
                miLista.add(aux);
                encontrado = true;
        }
        } catch(Exception e){
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        return encontrado;
    }

    public Boolean validarNombrePrenda(String nombre, DataSource dataSource){
        return nombre.matches("[a-zA-Z]*");
    }
}