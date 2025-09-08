#NTTDATA

#AMBIENTE

- Springboot (v3.4.10).
- Java (jdk-17.0.16).
- Maven como gestor de dependencias y para generar build (v3.9.9).
- Persistencia JPA con Hibernate.
- Banco de datos persistente con H2.
- Swagger UI.

#IDE
Herramientas y configuraciones:

- IntelliJ IDEA Community (Editor utilizado para el desarrollo).
- Postman (Para probar ejecuciones sobre los endpoints).
- Java version 17.0.16 (con variable JAVA_HOME configurada).
- Maven version 3.9.9 (con variable de entorno configurada).

#REPOSITORIO Y EJECUCION

Clonar el repositorio en un directorio local
Cargar proyecto en IDE
Abrir una terminal y ejecutar el siguiente comando: 

**mvn spring-boot:run**

#RECURSOS ADICIONALES

En la carpeta "recursos" se encuentran los siguientes recursos:

- Flujo
- Diagrama de Flujo 
- Postman Collection
- OpenAPI de servicios
- Documento de pruebas unitarias

#TEST UNITARIOS

**CRECION DE USUARIO**
Como regla este tiene que tener obligatoriamente los campos "nombre", "correo" y "contraseña". Estos campos estan con validaciones gestionadas con expresiones regulares y validan formato.

Condiciones:
Nombre: No puede ser null, blanco/vacio, con numeros o caracteres especiales. Si soporta espacio para nombres compuestos.
Correo: No puede ser null, blanco/vacio, debe tener una estructura "correo@empresa.dominio".
Contraseña:No puede ser null, blanco/vacio, debe tener un largo minimo de 8 caracteres, 1 mayuscula y 1 caracter especial.

Del caso de recibir numero telefonico, este valida campo "numero", "codigoCiudad" y "codigoPais" todos estos deben ser numeros.

# Acceso a consola H2.

Para acceder a la consola de H2, cargamos la siguiente ruta en el navegador:

http://localhost:9001/h2-console/login.jsp 
Usuario: admin
Password:

# Swagger

Para visualizar el swagger el servicio, accederemos a la interfaz cargando el numero de puerto en un navegador.

http://localhost:9001/swagger-ui/index.html