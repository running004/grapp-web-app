package grapp.grapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusquedaPrenda
{
    private List<Prenda> miLista ;
    private String nombre, emailUser;
    public BusquedaPrenda(){
        this.miLista = new ArrayList<Prenda>();

    }
    public BusquedaPrenda(String nombre, String emailUser){
        miLista = new ArrayList<Prenda>();
        this.nombre=nombre;
        this.emailUser=emailUser;

    }
    public List<Prenda> getmiLista(){
        return this.miLista;
    }
    public String getnombre(){
        return nombre;
    }
    public String getemailUser(){
        return emailUser;
    }
    public void setmiLista(List<Prenda> miLista){
        this.miLista=miLista;
    }
    public void setnombre(String nombre){
        this.nombre=nombre;
    }
    public void setemailUser(String emailUser){
        this.emailUser=emailUser;
    }
    public String BuscarPorUsuario(String nombre,DataSource dataSource){ // cambiarlo a strings
        if(!validarMail(nombre)) return "El formato de usuario es erroneo. Contiene caracteres invalidos";
        if(!validarExisteUsuario(nombre,dataSource)) return "Este usuario no existe";
        if(!rellenarPorUsuario(nombre,dataSource)) return "El usuario no tiene subida ninguna prenda";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return null;
     }
     public String BuscarPorNombre(String nombre,DataSource dataSource){ // cambiarlo a strings
        if(!validarNombrePrenda(nombre, dataSource)) return "El formato del nombre de la prenda no es valido";
        if(nombre.length()>50) return "El nombre no puede tener mas de 50 caracteres."; // falta poner que no haya caracteres raros
        if(!rellenarPorNombre(nombre,dataSource)) return "No existen prendas con este nombre";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return null;
     }
     public String BuscarPorNombreyUsuario(String nombre,String emailUser, DataSource dataSource){ // cambiarlo a strings
        if(!rellenarPorNombreyUsuario(nombre,emailUser,dataSource)) return "El nombre y/o el usuario no existe";
        // buscar en la tabla de prendas por ese usario y rellenar la lista
         return null;
     }
     public boolean validarExisteUsuario(String emailUser, DataSource dataSource){
        boolean encontrado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIOS WHERE email='"+emailUser+"' ");
            if(rs.next()) encontrado = true;
            c.close();
        } catch(Exception e){
            System.out.println("No existe este usuario.");
        }
        return encontrado;
    }
    
    public Boolean rellenarPorUsuario(String usuario, DataSource dataSource){
        boolean encontrado = false;
        miLista = new ArrayList<Prenda>();
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRENDAS WHERE propietario='"+usuario+"' ");
            while(rs.next()){
                Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("propietario") ,rs.getString("imgurl"),rs.getString("descripcion"));
                miLista.add(aux);
                encontrado = true;
        }
        c.close();
        } catch(Exception e){
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        
        return encontrado;
    }

    public Boolean rellenarPorNombre(String nombre, DataSource dataSource){
        boolean encontrado = false;
        miLista = new ArrayList<Prenda>();
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRENDAS WHERE nombre='"+nombre+"' ");
            while(rs.next()){
                Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("propietario") ,rs.getString("imgurl"),rs.getString("descripcion"));
                miLista.add(aux);
                encontrado = true;
        }
        c.close();
        } catch(Exception e){
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        return encontrado;
    }
    public Boolean rellenarPorNombreyUsuario(String nombre,String emailUser, DataSource dataSource){
        boolean encontrado = false;
        miLista = new ArrayList<Prenda>();
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRENDAS WHERE nombre='"+nombre+"' AND propietario='"+emailUser+"' ");
            while(rs.next()){
                Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("propietario") ,rs.getString("imgurl"),rs.getString("descripcion"));
                miLista.add(aux);
                encontrado = true;
        }
        c.close();
        } catch(Exception e){
            System.out.println("Fallo al buscar la prenda por nombre");
        }
        return encontrado;
    }
    public void todo(DataSource dataSource){
        
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRENDAS");
            while(rs.next()){
               Prenda aux= new Prenda(rs.getString("nombre") ,rs.getString("propietario") ,rs.getString("imgurl"),rs.getString("descripcion"));
                miLista.add(aux);
        }
        c.close();
        } catch(Exception e){

        }
    }
  
    public Boolean validarNombrePrenda(String nombre, DataSource dataSource){
        return nombre.matches("[a-zA-Z0-9]*");
    }
    public boolean validarMail(String email) {
        if (!email.equals("")) {
             Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
             + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
             Matcher mather = pattern.matcher(email);
             return mather.find();
         } else {
             return false;
         }
     }
}