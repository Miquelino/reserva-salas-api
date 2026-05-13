# ===== ETAPA DE BUILD =====
# ===== Usa Maven + Java 17 para compilar o projeto. =====
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# ===== Cria o JAR do Spring Boot. =====
RUN mvn clean package -DskipTests


# ===== ETAPA FINAL =====
# ===== Aqui usamos só o JRE:
        #
        #menor
        #mais rápido
        #mais leve =====
FROM eclipse-temurin:17-jre

WORKDIR /app

# ===== 👉 isso é MULTI-STAGE BUILD
        #
        #Muito importante. =====
COPY --from=build /app/target/*.jar app.jar

# ===== Documenta a porta da API. =====
EXPOSE 8080

# ===== Inicia a aplicação automaticamente. =====
ENTRYPOINT ["java", "-jar", "app.jar"]