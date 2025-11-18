FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Imagen base de Java (OpenJDK 17, recomendada para Spring Boot 3.x)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

COPY --from=build /app/target/*.jar lavadero-1.0.0.jar

# Exponer el puerto requerido por Render
EXPOSE 8080

# Render pasa PORT como variable de entorno. Spring Boot debe levantar en ese puerto.
ENV PORT=8080

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]