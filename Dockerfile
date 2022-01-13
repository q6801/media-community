FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/media-community-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT java -jar /app.jar --jasypt.encryptor.password=$pwd