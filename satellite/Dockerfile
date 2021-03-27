# For Java 8, try this
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/satellite-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dserver.port=$PORT","-Dspring.profiles.active=prod","-Xmx300m -Xms300m","-jar","app.jar"]