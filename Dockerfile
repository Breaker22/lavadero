# Imagen base de Java (OpenJDK 17, recomendada para Spring Boot 3.x)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR generado (cambiá "app.jar" por el nombre real)
COPY target/*.jar app.jar

# Exponer el puerto requerido por Render
EXPOSE 8080

# Render pasa PORT como variable de entorno. Spring Boot debe levantar en ese puerto.
ENV PORT=8080

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]