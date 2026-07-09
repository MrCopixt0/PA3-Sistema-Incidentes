# 🛠️ Sistema de HelpDesk y Gestión de Incidentes (helpdesk-backend)

Este proyecto consiste en una API REST (Headless Backend) diseñada bajo una arquitectura desacoplada para automatizar la priorización y distribución inteligente de tickets de soporte técnico. 

La aplicación calcula de forma ponderada la prioridad de cada incidencia registrada y la asigna al agente disponible más calificado (Senior o Junior) con menor volumen de carga laboral activa.

---

## 🚀 Tecnologías y Herramientas Utilizadas

*   **Lenguaje:** Java 17
*   **Framework Principal:** Spring Boot 3.4.0 (Starter Web, Starter Data JPA)
*   **Gestor de Proyectos:** Apache Maven
*   **Base de Datos:** H2 Database (En memoria)
*   **Pruebas Unitarias:** JUnit 5 y Mockito
*   **Contenerización:** Docker (Construcción Multi-Stage)

---

## ⚙️ Requisitos Previos

Antes de ejecutar la aplicación, asegúrese de tener instalado:
*   [Java Development Kit (JDK) 17](https://adoptium.net/temurin/releases/?version=17) o superior.
*   [Apache Maven 3.9](https://maven.apache.org/download.cgi) (opcional si utiliza la construcción con Docker).
*   [Docker Desktop](https://www.docker.com/products/docker-desktop/) en ejecución.

---

## 🧪 Pruebas Unitarias

Para ejecutar el arnés de pruebas automatizadas y asegurar la consistencia del dominio de negocio localmente, puede abrir su terminal en la raíz del proyecto y correr el siguiente comando de Maven:

mvn clean test

🐳 Despliegue con Docker
Para compilar y ejecutar el microservicio utilizando Docker, ejecute los siguientes comandos desde la raíz del proyecto. El archivo Dockerfile utiliza una compilación multi-etapa para mantener la imagen final ligera y optimizada.

1. Construir la imagen de Docker
    docker build -t helpdesk-backend:1.0-trabajoFinal .
2. Ejecutar el contenedor
    docker run -d -p 8080:8080 --name helpdesk-container helpdesk-backend:1.0-trabajoFinal

💻 Pruebas de Integración y Validación
Este proyecto utiliza una base de datos H2 en memoria, por lo que la base de datos se inicializa vacía en cada arranque de la aplicación.
A continuación se detallan los comandos para validar el correcto funcionamiento de la lógica de asignación automática de tickets mediante solicitudes HTTP.

Opción A: Comandos para Windows (PowerShell)

1. Registrar un agente (Perfil Senior):
Invoke-RestMethod `
  -Uri "http://localhost:8080/api/agentes" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"nombre":"Carlos","rol":"Senior","cargaTrabajo":0}'
2. Registrar e inyectar un ticket de alta prioridad:
Invoke-RestMethod `
  -Uri "http://localhost:8080/api/tickets" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"descripcion":"Fallo crítico en el mainframe","impacto":5,"gravedad":5}'

Opción B: Comandos para Linux / macOS (cURL)
1. Registrar un agente (Perfil Senior):
curl -X POST http://localhost:8080/api/agentes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Carlos","rol":"Senior","cargaTrabajo":0}'
2. Registrar e inyectar un ticket de alta prioridad:
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"Fallo crítico en el mainframe","impacto":5,"gravedad":5}'

🔍 Puntos de Acceso (Endpoints)
Una vez desplegada y en funcionamiento, puede interactuar con el sistema a través de las siguientes rutas locales:

## 🔍 Puntos de Acceso

| Recurso | Método | URL | Descripción |
|----------|:------:|-----|-------------|
| Consola de BD H2 | Web | `http://localhost:8080/h2-console` | Interfaz gráfica para administrar la base de datos H2 en memoria. |
| Listar Agentes | GET | `http://localhost:8080/api/agentes` | Devuelve todos los agentes registrados junto con su carga laboral actual. |
| Registrar Agente | POST | `http://localhost:8080/api/agentes` | Registra un nuevo agente de soporte en la base de datos. |
| Listar Tickets | GET | `http://localhost:8080/api/tickets` | Devuelve un listado completo de todos los tickets registrados. |
| Crear y Asignar Ticket | POST | `http://localhost:8080/api/tickets` | Calcula automáticamente la prioridad del ticket y lo asigna al agente más adecuado según la lógica implementada. |

📌 Propiedades de conexión para la consola H2:
JDBC URL: jdbc:h2:mem:helpdeskdb
User Name: copixto
Password: (Dejar en blanco)

Nota Técnica
Este proyecto corresponde a un Headless Backend, por lo que no incluye una interfaz gráfica de usuario (Frontend). Toda la interacción con el sistema se realiza mediante la API REST utilizando herramientas de consola como PowerShell/cURL, o clientes gráficos especializados como Postman o Insomnia.

Proyecto desarrollado como parte de la entrega de Construcción de Software. Ante cualquier inconveniente o duda con el despliegue de los contenedores, puede abrir un Issue en este repositorio.
¡Gracias por revisar el proyecto y perdonen si hay algun problema, soy nuevo en esto! 👾
