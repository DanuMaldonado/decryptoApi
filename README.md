Proyecto: API de Gestión de Comitentes y Mercados

1. Descripción del Proyecto

Este proyecto es una API REST desarrollada con Spring Boot que gestiona entidades relacionadas con comitentes y mercados en distintos países. Los comitentes están asociados a uno o más mercados, y los mercados pertenecen a un país específico. La API permite crear, leer, actualizar y eliminar (CRUD) las entidades de Comitente y Mercado, así como obtener estadísticas de distribución de comitentes por país y mercado.

Además, el proyecto incluye un cálculo automático del porcentaje de participación de cada mercado y país, y un endpoint para obtener estas estadísticas.

2. Funcionalidades principales

    Gestión de Comitentes: permite agregar, editar, listar y eliminar comitentes.
    Gestión de Mercados: permite agregar, editar, listar y eliminar mercados.
    Gestión de Países: permite obtener la lista de países permitidos (Argentina y Uruguay).
    Estadísticas: proporciona un endpoint que muestra la distribución de comitentes por mercado y país en porcentajes.
    Documentación de API: generada automáticamente con OpenAPI (Swagger).

3. Requisitos Previos

Para ejecutar este proyecto localmente, necesitarás tener instalados los siguientes componentes:

    Java 17 o superior.
    Maven 3.8.x o superior.
    MySQL 

4. Configuración de Base de Datos

	Este proyecto está configurado para usar MySQL y se conectara a la base de datos previamente configurada en Google Cloud, pero también se puede utilizar una base de datos H2 para el desarrollo y pruebas. La configuración se puede modificar en el archivo application.properties.

4.1. Configuración para MySQL

	Instala MySQL y crea una base de datos:

		CREATE DATABASE decrypto;

4.2. Actualiza el archivo src/main/resources/application.properties con las credenciales de tu base de datos MySQL:

	properties

	spring.datasource.url=jdbc:mysql://localhost:3306/decrypto
	spring.datasource.username=root
	spring.datasource.password=tu_contraseña
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql=true

4.3. Si prefieres utilizar H2 (para desarrollo rápido o pruebas):

	properties

	spring.datasource.url=jdbc:h2:file:~/decrypto-db
	spring.datasource.driverClassName=org.h2.Driver
	spring.datasource.username=sa
	spring.datasource.password=
	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
	spring.h2.console.enabled=true

5. Instrucciones para Ejecutar el Proyecto Localmente

    5.1. Clonar el repositorio:

		git clone https://github.com/usuario/decrypto-api.git
		cd decrypto-api

	5.2. Compilar el proyecto con Maven:

		mvn clean install

	5.3. Ejecutar la aplicación:

		mvn spring-boot:run

	5.4. Acceder a la documentación de la API (Swagger UI):

		Puedes acceder a la documentación generada por Swagger en la siguiente URL:

		http://localhost:8080/swagger-ui.html

6. Endpoints Principales

	Puedes utilizar la URL de http://localhost:8080 con postman o tambien la generada mediante Google Cloud: https://decryptoapiv1.rj.r.appspot.com

	6.1. Comitentes

		GET /api/comitentes: Lista todos los comitentes.
		POST /api/comitentes: Crea un nuevo comitente.
		PUT /api/comitentes/{id}: Actualiza un comitente existente.
		DELETE /api/comitentes/{id}: Elimina un comitente por ID.

	6.2. Mercados

		GET /api/mercados: Lista todos los mercados.
		POST /api/mercados: Crea un nuevo mercado.
		PUT /api/mercados/{id}: Actualiza un mercado existente.
		DELETE /api/mercados/{id}: Elimina un mercado por ID.

	6.3. Países

    	GET /api/paises: Lista los países permitidos (Argentina y Uruguay).

	6.4. Estadísticas

   		 GET /stats: Muestra estadísticas de distribución de comitentes por país y mercado.


