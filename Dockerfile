FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/media-community-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT java -Dkms.url=ws://13.209.253.79:8888/kurento -jar /app.jar --jasypt.encryptor.password=$pwd