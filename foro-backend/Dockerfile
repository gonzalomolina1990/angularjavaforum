# Usa una imagen oficial de Java 17 como base
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia todo el contenido de foro-backend al contenedor
COPY . .

# Da permisos de ejecución al Maven Wrapper (necesario en Linux)
RUN chmod +x mvnw

# Compila el proyecto y genera el JAR
RUN ./mvnw clean package -DskipTests

# Expone el puerto 8080 (el estándar de Spring Boot)
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "target/$(ls target | grep '.jar$' | head -n 1)"]