package grapp.grapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
public String comprobarDatos(){
    if(this.nombre.length()>50) return "El nombre no puede tener mas de 50 caracteres."; // falta poner que no haya caracteres raros
    Pattern pattern = Pattern.compile("^[\\sa-zA-Z0-9()]+$");
    Matcher mather = pattern.matcher(this.nombre);
    if(!mather.find()) return "El nombre contiene caracteres invalido, deben ser [A-Za-z0-9()]";
    if(this.descripcion.length()> 280) return "La descripcion no puede tener mas de 280 caracteres.";
    return null;
}

    
    public String insertPrenda(String nombre, String usuario, String descripcion, String foto, DataSource dataSource){
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO PRENDAS VALUES ('"+ nombre+ "', '" + foto +"', '" + descripcion +"', '" + usuario +"')");
            return "Prenda insertada correctamente";
        } catch(Exception e){
            return "Fallo al insertar prenda, recuerde que debe ser un nombre y descripcion válida";
        }

    }
    
    public boolean searchPrendaPorNombre(String nombre, String emailUser, DataSource dataSource){
        boolean existe = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRENDAS WHERE nombre='"+nombre+"'AND propietario='"+emailUser+"' ");
            if(rs.next()){
                if(rs.getInt(1) != 0)existe = true;
            } 
        } catch(Exception e){
            System.out.println("Prenda con el mismo nombre");
        }
        return existe;
    }
}