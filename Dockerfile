# Fase 1: Build dell'applicazione
FROM eclipse-temurin:17-jdk-jammy AS build
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Fase 2: Esecuzione
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]