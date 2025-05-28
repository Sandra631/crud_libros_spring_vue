# Etapa 1: compilar el proyecto con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: imagen para ejecutar el JAR
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Establece el puerto que usará Render
EXPOSE 8080
ENV PORT=8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
