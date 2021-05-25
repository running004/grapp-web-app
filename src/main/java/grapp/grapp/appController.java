package grapp.grapp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//nuevo
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class appController implements ErrorController{
    Boolean usuarioLoggeado = false;
   
    //Para en el header no mostrar el boton de login cuando el usuario haya iniciado sesion
    void botonLog(Model model, HttpServletRequest request){   
        Boolean usuarioLoggeado = request.getSession().getAttribute("email")==null?false:true;
        model.addAttribute("usuarioLogin",usuarioLoggeado);
        model.addAttribute("username", request.getSession().getAttribute("email"));
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;
  
    @Autowired
    private DataSource dataSource;

    @GetMapping(value= "/")
    String index(Model model, HttpServletRequest request){
        model.addAttribute("usuarioLogin", false);
        model.addAttribute("key", "prueba");
        List<String> listado = new ArrayList<String>();
        listado.add("Pagina principal: vuelve a la página principal");
        listado.add("Subir fotos: te permite subir una foto devolviendo un id");
        listado.add("Ver fotos: te permite ver las fotos subidas mediante id");
        model.addAttribute("features", listado);
        botonLog(model,request);
        return "index.html";
    }

    @GetMapping(value="/MiArmario")
    String MiArmario(Model model,@Valid formulario formulario, HttpServletRequest request){
        model.addAttribute("usuarioLogin", false);
        botonLog(model,request);
        return "MiArmario.html";
    }

    @GetMapping(value="/searchPrenda")
    String searchPrenda(Model model,@Valid formulario formulario, HttpServletRequest request){        
        botonLog(model,request);
        return "searchPrenda";
    }
    
    @GetMapping(value="/upload")
    String upload(Model model,@Valid formulario formulario, HttpServletRequest request){        
        botonLog(model,request);
        return "upload.html";
    }
    @GetMapping(value="/signup")
    String signup(Model model, HttpServletRequest request){ 
        User usuario = new User();
		model.addAttribute("usuario", usuario);  
        botonLog(model,request);     
        return "signup.html";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String crearUsuario(User usuario,Model model, HttpServletRequest request) {
        model.addAttribute("usuario", new User());
        String comprobacion = usuario.comprobarDatos();
        if(comprobacion !=null){
            model.addAttribute("errmessg", comprobacion);
            return "signup.html";
        }
        Boolean existe=usuario.searchUserForSignUp(dataSource);
        if(existe){
            //mandar error al html de user ya creado
            model.addAttribute("errmessg", "Usuario ya existente");
            return "signup.html";
        }
        else{
            model.addAttribute("yaCreado", false);
            model.addAttribute("errorDatos", false);
            usuario.insertUser(dataSource);
        }
        usuarioLoggeado = true;
        botonLog(model,request);
        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String crearFormularioUsuario(Model model, HttpServletRequest request) {
		User usuario = new User();
		model.addAttribute("usuario", usuario);
        botonLog(model,request);
		return "login.html"; 
	}

    // LOGIN
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, User usuario, HttpServletRequest request) {
        
        // por usuario me entran el correo y la contraseña
        System.out.println(usuario.getEmail());
        //comprobamos validez
        try{
            if(usuario.searchUser(dataSource)){
                request.getSession().setAttribute("email", usuario.getEmail());
                botonLog(model,request);
                return "index.html";
            } else {
                model.addAttribute("error", "No existe esa cuenta");
                model.addAttribute("usuario", new User());
            return "login.html";
            }
        }catch(Exception e){
            model.addAttribute("error", usuario.comprobarDatos());
            model.addAttribute("usuario", new User());
            return "login.html";
        }

        //quedaria iniciar la sesion
    }
    @GetMapping(value= "/session1")
    String session1(Model model, HttpServletRequest request){
        request.getSession().setAttribute("test","testing");

        return "session1.html";
    }

    @GetMapping(value= "/session2")
    String session2(Model model, HttpServletRequest request){
        model.addAttribute("msg", request.getSession().getAttribute("test"));
        return "session2.html";
    }


    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return "/error";
    }

    @Bean
    public DataSource dataSource() throws SQLException {
      if (dbUrl == null || dbUrl.isEmpty()) {
        return new HikariDataSource();
      } else {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
      }
    }

}
