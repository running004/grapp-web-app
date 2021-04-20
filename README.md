[![Codacy Security Scan](https://github.com/bdmariobd/grapp-web-app/actions/workflows/codacy-analysis.yml/badge.svg)](https://github.com/bdmariobd/grapp-web-app/actions/workflows/codacy-analysis.yml)
[![CodeQL](https://github.com/bdmariobd/grapp-web-app/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/bdmariobd/grapp-web-app/actions/workflows/codeql-analysis.yml)
[![tests](https://github.com/bdmariobd/grapp-web-app/actions/workflows/tests.yml/badge.svg)](https://github.com/bdmariobd/grapp-web-app/actions/workflows/tests.yml)

# GuardaRopApp

## Introduccion

GuardaRopApp es una aplicación dirigida al público general interesado en la moda y las redes sociales que le ayudará a gestionar su armario e inspirarse a la hora de crear y escoger outfits.

Para satisfacer esta necesidad proponemos una aplicación que permitirá guardar toda tu ropa en tu armario virtual. Podrás crear conjuntos con esta ropa guardada y compartirlos para que otros usuarios puedan verlos y guardarlos como favoritos.

Si a un usuario le gusta mucho una prenda de un outfit, podrá chatear con el creador del conjunto, ya sea para preguntar donde adquirió la prenda o para solicitar un intercambio, dando la opción de pedirle intercambio por alguna/s prendas de su armario y meter una cantidad de dinero. A la hora de subir un outfit también podrás etiquetar ropa para hacer saber a otros usuarios que estás dispuesto a desprenderte de una prenda. La aplicación contará con un potente buscador de prendas y de estilos para adaptarse a las necesidades de todos los usuarios.

Si un usuario está indeciso a la hora de vestirse, la aplicación podrá recomendarle un outfit de su armario, intentando que se adapte a las necesidades requeridas(puede poner: formal y solo recomendarle formal), intentando no repetir demasiados días la misma ropa. La aplicación también podrá guardar y presentar visualmente datos de los usuarios, como sus prendas más usadas, sus estilos favoritos, sus marcas de ropa favoritas, etc.

## Arquitectura

Esta aplicación web seguirá una arquitectura multicapa utilizando el patrón MVC.

## Herramientas

* Java: la aplicación web está diseñada en Java
* Spring MVC: framework que optimiza el proceso de aplicaciones web que implementan el patrón MVC, ofreciendo un Front Controller ya montado.
* Maven: herramienta que simplifica los procesos de compilar y ejecutar ejecutables a partir de un código fuente. Viene muy bien para lanzar una aplicación a un domino(deploy), ya que es capaz de tener las dependencias específicadas en un fichero denominado POM.xlm, y simplemente al compilar descargará lo que necesite. Además, ofrece herramientas como test unitarios, que serán de utilidad. Combinado con Spring, podrémos compilar y lanzar a un localhost para probar con muchísima facilidad usando `mvn spring-boot:run`
* Thymeleaf: framework de plantillas html que utilizamos para conectar el contenido de las vistas con el resto con mayor facilidad. Permite la integración con Spring MVC.
* Heroku: servicio utilizado para desplegar la aplicación en un dominio. También ofrece una base de datos gratuita y desplegar la aplicación directamente desde un repositorio GitHub(que será este). Esta página estará desplegada en el siguiente enlace [https://guardaropapp.herokuapp.com/](https://)

## Ayuda

### Controller

Se encuentra en [src\main\java\grapp\grapp](https://github.com/bdmariobd/grapp-web-app/tree/master/src/main/java/grapp/grapp)

Aqui crearemos las rutas de nuestra aplicación, creando su metodo GET con la anotacion`@GetMapping` siempre para acceder a la ruta:

```java
@GetMapping(value="/upload")
    String upload(Model model,@Valid formulario formulario){
        return "upload.html";
    }
```

Y su metodo POST con la anotación `@PostMapping` en caso de tener que hacer alguna request al servidor:

```java
@PostMapping(value="/upload")
    String uploadPost(Model model, @Valid formulario formulario, BindingResult bindingResult){
        //TODO upload photo to the server
        //CODIGO DE ACCESO PARA POSTEAR FOTO
        //TODO get photo id 
        String generatedId="0";
        model.addAttribute("id", generatedId);
        return "upload.html";
    }
```

Estos metodos devuelven un String que indica el nombre de la vista a renderizar accediendo a esa ruta.

El parámetero `Model model` sirve para pasar contenido del controlador a la vista. Por ejemplo, `model.addAttribute("id", generatedId);` le envía a la vista un campo 'id' con el valor de una id.

### Vistas

Se encuentran en [src/main/resources/templates](https://github.com/bdmariobd/grapp-web-app/tree/master/src/main/resources/templates)
