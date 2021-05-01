package grapp.grapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Prenda
{
    private String nombre, emailUser, foto, descripcion;
    public Prenda(){
    }
    public Prenda(String nombre,String emailUser, String foto, String descripcion) {
        this.nombre=nombre;
        this.emailUser = emailUser;
        this.foto = foto;
        this.descripcion=descripcion;
    }

    public String getemailUser() {
        return emailUser;
    }
    public String getfoto() {
        return foto;
    }
    public String getnombre() {
        return nombre;
    }
    public String getdescripcion() {
        return descripcion;
    }
    public void setfoto(String foto) {
        this.foto = foto;
    }
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }
    public void setemailUser(String emailUser) {
        this.emailUser = emailUser;
    }
    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
public boolean comprobarDatos(){
    if(this.nombre.length()>50) return false; // falta poner que no haya caracteres raros
    if(this.descripcion.length()> 280) return false;
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