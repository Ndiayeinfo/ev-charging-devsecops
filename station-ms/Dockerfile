FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app

# Le JAR est produit par Maven dans target/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/app/app.jar"]

