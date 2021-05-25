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
    Boolean buscado=true;
    //Para en el header no mostrar el boton de login cuando el usuario haya iniciado sesion
    void botonLog(Model model, HttpServletRequest request){
        Boolean usuarioLoggeado = request.getSession().getAttribute("email")==null?false:true;
        model.addAttribute("usuarioLogin",usuarioLoggeado);
        model.addAttribute("username", request.getSession().getAttribute("email"));
        
        BusquedaPrenda busqueda= new BusquedaPrenda(); 
        busqueda.todo(dataSource);
        model.addAttribute("busqueda", busqueda);
        
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;
  
    @Autowired
    private DataSource dataSource;

    @GetMapping(value="/")
    String index(Model model,@Valid formulario formulario, HttpServletRequest request){
        model.addAttribute("usuarioLogin", false);
        buscado=false;
        botonLog(model,request);
        BusquedaPrenda busqueda= new BusquedaPrenda();
        busqueda.todo(dataSource);
		model.addAttribute("miLista", busqueda.getmiLista());
        System.out.println(busqueda.getmiLista());

        return "index.html";
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    String BuscarPrenda(BusquedaPrenda busqueda, Model model, HttpServletRequest request){
        model.addAttribute("busqueda", new BusquedaPrenda());//
        if(busqueda.getnombre()!="" && busqueda.getemailUser()!=""){ // busqueda por nombre y usuario
           if(busqueda.BuscarPorNombreyUsuario(busqueda.getnombre(), busqueda.getemailUser(), dataSource)==null){
            List miLista=busqueda.getmiLista();
            model.addAttribute("miLista", miLista);
           }
           else{
            model.addAttribute("errmessg", busqueda.BuscarPorNombre(busqueda.getnombre(), dataSource));
           }
        }
        else if(busqueda.getnombre()!="" ){ //busqueda por nombre
            if( busqueda.BuscarPorNombre(busqueda.getnombre(), dataSource)==null){
                List miLista=busqueda.getmiLista();
                model.addAttribute("miLista", miLista);
               }
               else{
                model.addAttribute("errmessg", busqueda.BuscarPorNombre(busqueda.getnombre(), dataSource));
               }
        }
        else if(busqueda.getemailUser()!="" ){ // busqueda por usuario
            if(busqueda.BuscarPorUsuario(busqueda.getemailUser(), dataSource)==null){
                List miLista=busqueda.getmiLista();
                model.addAttribute("miLista", miLista);
               }
               else{
                model.addAttribute("errmessg", busqueda.BuscarPorUsuario(busqueda.getemailUser(), dataSource));
               }
        }
    else { 
        busqueda.todo(dataSource);
        model.addAttribute("miLista",busqueda.getmiLista());
    }
    botonLog(model,request);     
    return "index.html";
    }
    /*
    @RequestMapping(value = "/", method = RequestMethod.POST)
    String MostrarTodo( Model model, HttpServletRequest request){
        BusquedaPrenda busqueda = new BusquedaPrenda();
        busqueda.todo(dataSource);
        model.addAttribute("miLista", busqueda.getmiLista());
        System.out.println(busqueda.getmiLista());
       
        return "index.html";
    }*/

    @GetMapping(value="/MiArmario")
    String MiArmario(Model model,@Valid formulario formulario, HttpServletRequest request){
        model.addAttribute("usuarioLogin", false);
        BusquedaPrenda busqueda= new BusquedaPrenda();
        busqueda.setemailUser((String) request.getSession().getAttribute("email"));
        busqueda.BuscarPorUsuario(busqueda.getemailUser(), dataSource);
        model.addAttribute("miLista", busqueda.getmiLista());
        botonLog(model,request);
        System.out.println(busqueda.getmiLista());
        return "MiArmario.html";
    }

    @RequestMapping(value = "/MiArmario", method = RequestMethod.POST)
    String BuscarPrendaMiArmario(BusquedaPrenda busqueda, Model model, HttpServletRequest request){
        busqueda.setemailUser((String) request.getSession().getAttribute("email"));
        if(busqueda.getnombre()!="" && busqueda.getemailUser()!=null){ // busqueda por nombre y usuario
           if(busqueda.BuscarPorNombreyUsuario(busqueda.getnombre(), busqueda.getemailUser(), dataSource)==null){
            List miLista=busqueda.getmiLista();
            model.addAttribute("miLista", miLista);
           }
           else{
            model.addAttribute("errmessg", busqueda.BuscarPorNombre(busqueda.getnombre(), dataSource));
           }
        }
        else{
        if( busqueda.BuscarPorUsuario(busqueda.getemailUser(), dataSource)==null){
            List miLista=busqueda.getmiLista();
            model.addAttribute("miLista", miLista);
           }
           else{   // poner el user de la sesion
            model.addAttribute("errmessg", busqueda.BuscarPorUsuario(busqueda.getemailUser(), dataSource));
           }
        }
    botonLog(model,request);     
    return "MiArmario.html";
    }
    @GetMapping(value="/upload")
    String upload(Model model,@Valid formulario formulario, HttpServletRequest request){        
        Prenda prenda =new Prenda();
        model.addAttribute("prenda",prenda);
        botonLog(model,request);
        return "upload.html";
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String crearPrenda(Prenda prenda,Model model, HttpServletRequest request) {
        model.addAttribute("prenda", new Prenda());
        String comprobacion = prenda.comprobarDatos();
        prenda.setemailUser((String) request.getSession().getAttribute("email"));
        if(comprobacion !=null){
            model.addAttribute("errmessg", comprobacion);
            return "upload.html";
        }
        Boolean existe=prenda.searchPrendaPorNombre(prenda.getnombre(),prenda.getemailUser(), dataSource);
        if(existe){
            //mandar error al html de user ya creado
            model.addAttribute("errmessg", "Prenda con el mismo nombre");
            return "upload.html ";
        }
        else{
            model.addAttribute("yaCreado", false);
            model.addAttribute("errorDatos", false);
            String generatedId = imgUrlScraper.uploadImg(prenda.getImg());  
            String URL =  imgUrlScraper.getImageUrl(generatedId);
            prenda.setfoto(URL);
            String insertar=prenda.insertPrenda(prenda.getnombre(),prenda.getemailUser(),prenda.getdescripcion(),prenda.getfoto(),dataSource);
        }
        botonLog(model,request);
        BusquedaPrenda busqueda= new BusquedaPrenda(); 
        busqueda.todo(dataSource);
        model.addAttribute("busqueda", busqueda);
        return "MiArmario.html";
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
        BusquedaPrenda busqueda= new BusquedaPrenda(); 
        busqueda.todo(dataSource);
		model.addAttribute("busqueda", busqueda);
        return "login.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, User usuario, HttpServletRequest request) {
        // por usuario me entran el correo y la contraseña
        System.out.println(usuario.getEmail());
        //comprobamos validez
        try{
            if(usuario.searchUser(dataSource)){
                request.getSession().setAttribute("email", usuario.getEmail());
                botonLog(model,request);
                BusquedaPrenda busqueda= new BusquedaPrenda(); 
                busqueda.todo(dataSource);
                model.addAttribute("busqueda", busqueda);
                return "index.html";
            } 
            else if(usuario.comprobarDatosLogIn() != null){
                model.addAttribute("errmessg", usuario.comprobarDatosLogIn());
                model.addAttribute("usuario", new User());
                return "login.html";
            }
            else {
                model.addAttribute("errmessg", "El correo y la constraseña no coinciden");
                model.addAttribute("usuario", new User());
                return "login.html";
            }
            
            
        }catch(Exception e){
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