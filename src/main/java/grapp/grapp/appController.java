package grapp.grapp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  

    @Value("${spring.datasource.url}")
    private String dbUrl;
  
    @Autowired
    private DataSource dataSource;

    @GetMapping(value= "/")
    String index(Model model){
        model.addAttribute("usuarioLogin", false);
        model.addAttribute("key", "prueba");
        List<String> listado = new ArrayList<String>();
        listado.add("Pagina principal: vuelve a la página principal");
        listado.add("Subir fotos: te permite subir una foto devolviendo un id");
        listado.add("Ver fotos: te permite ver las fotos subidas mediante id");
        model.addAttribute("features", listado);
        return "index.html";
    }

    @GetMapping(value="/upload")
    String upload(Model model,@Valid formulario formulario){
        return "upload.html";
    }

    @PostMapping(value="/upload")
    String uploadPost(Model model, @Valid formulario formulario, BindingResult bindingResult){
        
        //bbddd
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            //upload photo
            String generatedId = imgUrlScraper.uploadImg(formulario.getImg());
            model.addAttribute("imgUrl", imgUrlScraper.getImageUrl(generatedId));
            //get id 
            String userID = formulario.getText();
            model.addAttribute("id", generatedId);
            model.addAttribute("userID", userID);

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS USUARIOS(email VARCHAR(10), contraseña VARCHAR(10))");
            stmt.executeUpdate("INSERT INTO imgs VALUES ('" + userID + "', '" + generatedId  + "')");
            ResultSet rs = stmt.executeQuery("SELECT count(*) as check FROM imgs WHERE idUser='"+userID+"' AND idImg='"+generatedId+"'");
            Boolean output = rs.next();
            if(output){
                model.addAttribute("exito", "Se ha insertado");
            }
            else{
                model.addAttribute("exito", "No se ha insertado");
            }

        } catch(Exception e){
            model.addAttribute("excepcion", e.getMessage());
        }
        return "upload.html";
    }

    @GetMapping(value="/see")
    String see(Model model,@Valid formulario formulario){        
        return "see.html";
    }

    
    @GetMapping(value="/favorites")
    String favorites(Model model,@Valid formulario formulario){        
        return "favorites.html";
    }
    
    @GetMapping(value="/message")
    String message(Model model,@Valid formulario formulario){        
        return "message.html";
    }

    @GetMapping(value="/signup")
    String signup(Model model,@Valid formulario formulario){        
        return "signup.html";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String crearFormularioUsuario(Model model) {
		User usuario = new User();
		model.addAttribute("usuario", usuario);
		return "login"; 
	}
//---------------------------------------------------------------------------
    //COMPROBAMOS EL INICIO DE SESION
    @RequestMapping(value = "/comprobarusuario", method = RequestMethod.GET)
    public String comprobarUsuario(Model model, User usuario) {
        usuarioLoggeado = true;
        model.addAttribute("usuarioLogin", usuarioLoggeado);
        System.out.println(usuario.getEmail());
        System.out.println(usuario.getContrasenia());
        usuario.hashContrasenia(usuario.getContrasenia());
        
        //aqui me haria un servicio que llame a la base de datos pasandole un mail y q compruebe que existe
        /*
        Usuario usu= serviceUsuario.findOne(usuario.getEmail());
        Usuario usuar = new Usuario();
        
        if(usu != null) {
        //AQUI COMPRUEBO EMAIL Y CONTRASEÑAS SON IGUALES SINO SON IGUALES LE DEVOLVEMOS AL LOGIN CON UN MENSAJE DE ERROR
            if((usuario.getEmail().equals(usu.getEmail())) &&
                    (usuario.getContrasenia().equals(usu.getContrasenia()))	) {
                return "redirect:AQUI PONES EL HTML QUE QUIERAS";
            }else {

                model.addAttribute("usuario", usuar);
                model.addAttribute("mensaje", "Usuario o contraseña invalidos");
                return "login"; 
            }
        }
        model.addAttribute("usuario", usuar);
        model.addAttribute("mensaje", "Usuario o contraseña invalidos");
        return "login"; 
        */
        return "index";

    }

//---------------------------------------------------------------------------







    @PostMapping(value="/see")
    String seePost(Model model, @Valid formulario formulario, BindingResult bindingResult){
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idImg FROM imgs WHERE idUser='" + formulario.getText() +"'");
            Map<String, String> imgUrlMap= new HashMap<String, String>();
            while(rs.next()){
                imgUrlMap.put(rs.getString("idImg"), imgUrlScraper.getImageUrl(imgUrlScraper.searchById(rs.getString("idImg"))));
            }
            model.addAttribute("urlMap", imgUrlMap);
        } catch(Exception e){
            model.addAttribute("excepcion", e.getMessage());
        }
        return "see.html";
    }

    //---------------------------------------------------------------------------------------------------

    //HTTP Error handle DO NOT TOUCH

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
