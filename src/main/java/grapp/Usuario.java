package grapp;

public class Usuario {
    private String nombre;
    private String apellidos;
    private String email;
    private String contraseña;

    Usuario(String nombre, String apellidos, String email, String contraseña){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contraseña = contraseña;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setapellidos(String apellidos){
        this.apellidos = apellidos;
    }
    public void setemail(String email){
        this.email = email;
    }
    public void setcontraseña(String contraseña){
        this.contraseña = contraseña;
    }

    public String getNombre(String nombre){
        return this.nombre;
    }

    public String getapellidos(String apellidos){
       return this.apellidos;
    }
    public String getemail(String email){
        return this.email;
    }
    public String getcontraseña(String contraseña){
        return this.contraseña;
    }

    public Boolean login(String email, String contraseña){
        return this.email.equals(email) && this.contraseña.equals(contraseña);
    }

}
