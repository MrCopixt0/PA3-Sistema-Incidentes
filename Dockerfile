# ==========================================
# Etapa 1: Compilación y empaquetado (Build)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el archivo pom.xml primero para aprovechar la caché de capas de Docker
COPY pom.xml .

# Descargar las dependencias necesarias de Maven
RUN mvn dependency:go-offline -B

# Copiar el código fuente del proyecto
COPY src ./src

# Compilar y empaquetar la aplicación en un archivo .jar (evitando los tests durante el build)
RUN mvn clean package -DskipTests

# ==========================================
# Etapa 2: Imagen final de ejecución (Runtime)
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el archivo .jar compilado desde la etapa anterior
# El nombre coincide con el artifactId y version declarados en tu pom.xml
COPY --from=build /app/target/helpdesk-backend-1.0.0.jar app.jar

# Exponer el puerto configurado en application.properties
EXPOSE 8080

# Definir el comando para iniciar la aplicación de Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]